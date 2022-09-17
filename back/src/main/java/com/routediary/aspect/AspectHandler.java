package com.routediary.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AspectHandler {

  // 메서드 실행시간 측정
  @Around("@annotation(com.routediary.annotation.LogExecutionTime)")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    Object proceed = joinPoint.proceed(); // @LogExecutionTime annotation이 붙은 메서드에만 실행되도록 설정

    stopWatch.stop();
    log.info("<< 메서드 실행시간 측정 -  " + stopWatch.getLastTaskTimeMillis() + "ms / 메서드 이름 : "
        + joinPoint.getSignature() + " >>");

    return proceed;
  }

  // 실행 성공한 메서드 log로 츌력
  @AfterReturning(value = "execution(* com.routediary..*.*(..))")
  public void afterReturningAdvice(JoinPoint joinPoint) {
    log.info("<< 실행 성공한 method 이름 : " + joinPoint.getSignature() + " >>");
  }

  // 실행 실패한 메서드 log로 출력
  @AfterThrowing(value = "execution(* com.routediary..*.*(..))", throwing = "e")
  public void afterAdvice(JoinPoint joinPoint, Exception e) {
    e.printStackTrace();
    log.error("<< 실행이 실패한 method 이름 : " + joinPoint.getSignature() + " >>");
    log.error("<< 실패 이유 : " + e.getMessage() + " >>");
  }
}
