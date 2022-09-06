package com.mf.aspect;
import com.mf.dto.WeblogDto;
import com.mf.queue.Tracking;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Component
@Aspect
@Slf4j
public class WeblogAspect {

    @Autowired
    private Tracking<WeblogDto> tracking;
    private ThreadLocal<Long> startTime = new ThreadLocal<>();
    private ThreadLocal<String> methodName = new ThreadLocal<>();
    private ThreadLocal<String> classInfo = new ThreadLocal<>();
    @Pointcut("execution(public * com.mf.controller.*.*(..))")
    public void weblogPointcut() {}

    @Before("weblogPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        methodName.set(joinPoint.getSignature().getName());
        classInfo.set(joinPoint.getSignature().getDeclaringTypeName());
    }

    @AfterReturning(pointcut = "weblogPointcut()")
    public void doAfterReturning() {
        long takeTime = System.currentTimeMillis() - startTime.get();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        WeblogDto weblogDto = WeblogDto.builder()
                .url(request.getRequestURI())
                .browser(userAgent.getBrowser().getName())
                .browserVersion(String.valueOf(userAgent.getBrowserVersion()))
                .ip(request.getRemoteAddr())
                .classInfo(classInfo.get())
                .os(userAgent.getOperatingSystem().getName())
                .takeTime(takeTime)
                .method(request.getMethod())
                .methodName(methodName.get())
                .build();
        tracking.put(weblogDto);
        log.info("[{}] request take {} s", weblogDto.getUrl(), weblogDto.getTakeTime()/1000);
    }

    @AfterThrowing(value = "weblogPointcut()", throwing = "throwable")
    public void doAfterThrowing(Throwable throwable){
        log.error("发生异常时间：{}", LocalDateTime.now());
        log.error("抛出异常：{}", throwable.getMessage());
    }

}
