package com.strichka.loging;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = Logger.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.strichka.repository.*.*(..))")
    private void loggingForRepositoryLayer() {}

    @Pointcut("execution(* com.strichka.service.*.*(..))")
    private void loggingForServiceLayer() {}

    @Pointcut("loggingForRepositoryLayer() || loggingForServiceLayer()")
    private void forAllApplication() {}

    @Before("forAllApplication()")
    public void before(JoinPoint joinPoint) {
        logger.info("Call method: " + joinPoint.getSignature().toShortString());
    }

}
