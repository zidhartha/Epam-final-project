package com.epam.rd.autocode.assessment.appliances.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingServices {

    // for all methods annotated with loggable
    @Pointcut("@annotation(com.epam.rd.autocode.assessment.appliances.aspect.Loggable)")
    public void loggableMethods(){
    }


    // pointcut for service methods
    @Pointcut("execution(* com.epam.rd.autocode.assessment.appliances.service..*(..))")
    public void serviceMethods() {}

    @Pointcut("execution(* com.epam.rd.autocode.assessment.appliances.controller..*(..))")
    public void controllerMethods() {}

    // for controller methods
    @Before("loggableMethods()")
    public void logBefore(JoinPoint joinPoint){
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs() ;
        log.info("Entering: {}.{}() with arguments: {}",
                className, methodName, Arrays.toString(args));
    }

    @AfterReturning(pointcut = "loggableMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Method {} returned successfully", methodName);
    }

    @AfterThrowing(pointcut = "loggableMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        log.error("Exception in {}.{}(): {}",
                className, methodName, exception.getMessage(), exception);
    }

    @Around("loggableMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            log.info("Method {}.{}() executed in {} ms", className, methodName, executionTime);
            return result;
        } catch (Throwable throwable) {
            long executionTime = System.currentTimeMillis() - startTime;
            log.error("Method {}.{}() failed after {} ms", className, methodName, executionTime);
            throw throwable;
        }
    }

    @Before("serviceMethods()")
    public void logServiceMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.debug("Service method called: {}", methodName);
    }

    @Before("controllerMethods()")
    public void logControllerMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        log.info("HTTP Request received: {}.{}",
                className.substring(className.lastIndexOf('.') + 1), methodName);
    }
}
