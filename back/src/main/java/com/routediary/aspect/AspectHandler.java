package com.routediary.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AspectHandler {

  // 실행 성공한 메서드 log로 츌력
  @AfterReturning(value = "execution(* com.routediary..*.*(..))")
  public void afterReturningAdvice(JoinPoint joinPoint) {
    log.info("<< 실행 성공한 method 이름 : " + joinPoint.getSignature() + " >>");
  }

  // 실행 실패한 메서드 log로 출력
  @AfterThrowing(value = "execution(* com.routediary..*.*(..))", throwing = "ex")
  public void afterAdvice(JoinPoint joinPoint, Exception ex) {
    log.error("<< 실행이 실패한 method 이름 : " + joinPoint.getSignature() + " >>");
    log.error("<< 실패 이유 : " + ex.getMessage() + " >>");
  }
}
