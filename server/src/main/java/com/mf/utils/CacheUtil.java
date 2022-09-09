package com.mf.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Objects;

public class CacheUtil {

    public static String getCacheKey(String cacheName, String key, ProceedingJoinPoint joinPoint) {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(key);
        EvaluationContext context = new StandardEvaluationContext();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        String [] parameterNames = discoverer.getParameterNames(methodSignature.getMethod());
        Object [] args = joinPoint.getArgs();
        for (int i = 0; i < Objects.requireNonNull(parameterNames).length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        return String.format(cacheName, expression.getValue(context));

    }
}
