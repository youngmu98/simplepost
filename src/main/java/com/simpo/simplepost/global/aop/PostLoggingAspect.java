package com.simpo.simplepost.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class PostLoggingAspect {
    @Pointcut("execution(* com.simpo.simplepost.post.controller.PostController.*(..))")
    private void targetMethod() {
    }

    @Before("targetMethod()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("[메서드 호출 전] 호출 클래스 {}", joinPoint.getTarget().getClass());
        log.info("[메서드 호출 전] 호출 메서드 {}", joinPoint.getSignature().getName());
    }

    @After("targetMethod()")
    public void logAfter(JoinPoint joinPoint) {
        log.info("[메서드 호출 후] 호출 메서드 {}", joinPoint.getSignature().getName());
    }
}
