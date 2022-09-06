package com.mf.aspect;

import com.common.base.BaseResponse;
import com.common.base.ResponseEnum;
import com.common.utils.JsonUtil;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.mf.annotation.MyRateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
public class RateLimiterAspect {
    private final Map<String, RateLimiter> limiterMap = Maps.newConcurrentMap();

    @Around("@annotation(myRateLimiter)")
    public Object doBefore(ProceedingJoinPoint joinPoint, MyRateLimiter myRateLimiter) throws Throwable {

       HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

       String resource = request.getRequestURI();
       RateLimiter rateLimiter = getRateLimiter(resource, myRateLimiter);

       boolean acquired = rateLimiter.tryAcquire(myRateLimiter.permits(), myRateLimiter.timeout(), myRateLimiter.timeUnit());
       if (!acquired) {
           log.error("{} 触发了限流", resource);
           this.outputResponse();
       }
       return joinPoint.proceed();
    }

    private RateLimiter getRateLimiter(String resource, MyRateLimiter myRateLimiter) {
        RateLimiter limiter = limiterMap.get(resource);
        if (limiter == null) {
            limiter = createRateLimiter(myRateLimiter);
            limiterMap.put(resource, limiter);
        }
        return limiter;
    }

    private RateLimiter createRateLimiter(MyRateLimiter myRateLimiter) {
        RateLimiter limiter;
        if (myRateLimiter.isWarmup()) {
            limiter = RateLimiter.create(myRateLimiter.permitPerSecond(), myRateLimiter.warmupPeriod(), myRateLimiter.warmupTimeUnit());
        } else {
            limiter = RateLimiter.create(myRateLimiter.permitPerSecond());
        }
        return limiter;
    }

    private void outputResponse() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            BaseResponse baseResponse = new BaseResponse(ResponseEnum.SYSTEM_BUSY);
            outputStream.write(JsonUtil.toJsonString(baseResponse).getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
