package com.capgemini.wsb.fitnesstracker.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(public * com.capgemini.wsb.fitnesstracker..*.*(..)) && @within(org.springframework.stereotype.Service)")
    public void logBeforeMethod(JoinPoint joinPoint) {
        String methodSignature = joinPoint.getSignature().toShortString();
        String args = getArguments(joinPoint);
        logger.info("Before method: {}({})", methodSignature, args);
    }

    @AfterReturning(pointcut = "execution(public * com.capgemini.wsb.fitnesstracker..*.*(..)) && @within(org.springframework.stereotype.Service)", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        String methodSignature = joinPoint.getSignature().toShortString();
        String args = getArguments(joinPoint);
        logger.info("After method: {}({}), returned: {}", methodSignature, args, result);
    }

    private String getArguments(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        StringBuilder argsStr = new StringBuilder();
        for (Object arg : args) {
            if (argsStr.length() > 0) {
                argsStr.append(", ");
            }
            argsStr.append(arg != null ? arg.toString() : "null");
        }
        return argsStr.toString();
    }
}