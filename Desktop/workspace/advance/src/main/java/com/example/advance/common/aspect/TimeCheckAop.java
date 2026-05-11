package com.example.advance.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TimeCheckAop {

    // 어떤 것을 : userService 하위의 메서드
    // 언제 : 메서드 실행 전후로
    // 어떻게 : executionTime 메서드 안의 내용을 실행 하겠다.

    // 메서드 실행 전후로 시간을 비교 분석하는 기능을 만들 것

/*
    @Around("execution(* com.example.advance.user.service.UserService.*(..))")
    public Object executionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        // 메서드 실행 전
        long start = System.currentTimeMillis();
        log.info("4번째 서비스 레이어 메서드 실행 전 AOP 로직 수행.");

        Object result = joinPoint.proceed(); // 실제 메서드 실행 -> Filter의 doFilter

        // 메서드 실행 후
        long end = System.currentTimeMillis();

        log.info("6번째 서비스 레이어 메서드 실행 후 AOP 로직 수행.");

        log.info("[AOP] {} 실행됨 In {} ms", joinPoint.getSignature(), end - start);

        return result;
    }
*/

    // 로그인 인증, 인가를 filter에서 수행
    // user email 수정하는 것 interceptor 수행이 되는 것
    // userEmailUpdate 메서드는 AOP에서 수행이 될것

    // 요청이 들어옴 -> JwtFILTER 통과함 -> OwnerCheckInterceptor ->
    // UserController 접근 -> userService의 userEmailUpdate
    // -> 타겟 대상인 서비스 레이어의 메서드가 실행 전 -> TimeCheckAop
    // userEmailUpdate 실행이 끝남 -> TimeCheckAop 실행 후 로직 수행
    // Controller에서 return 값 넘겨줌
    // jwtFilter 통과에서 postman으로 결과 전달

/*
    @Before("execution(* com.example.advance.user.service.UserService.*(..))")
    public void executionTime() throws Throwable {

        log.info("메서드 실행 전에만 수행됩니다.");
    }
*/

/*    @AfterReturning("execution(* com.example.advance.user.service.UserService.*(..))")
    public void executionTime() throws Throwable {

        log.info("메서드 실행 이후에 수행됩니다.");
    }
*/

/*
    @AfterThrowing("execution(* com.example.advance.user.service.UserService.*(..))")
    public void executionTime() throws Throwable {

        log.info("메서드 실행할 때 예외 발생.");
*/

    @After("execution(* com.example.advance.domain.user.service.UserService.*(..))")
    public void executionTime() throws Throwable {

//        log.info("메서드 실행 결과 상관 없이 무조건 실행.");
    }
}
