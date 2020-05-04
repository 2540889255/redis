package com.aynu.redis.aspect;


import com.aynu.redis.pojo.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AllAspect {

    @Pointcut("execution(* com.aynu.redis.service.UserServiceImpl.printPerson(..))")
    public void pointCut(){

    }

    @Before("pointCut() && args(user)")
    public void  before(JoinPoint joinPoint, User user){
        System.out.println(user.getUser_name());
        System.out.println("这是前置准备");
        user.setUser_name("这是经过before修饰的类");
    }

    @Around("pointCut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around before");
        joinPoint.proceed();
        System.out.println("around after");
    }

    @After("pointCut()")
    public void after(){
        System.out.println("这是后置准备");
    }

    @AfterReturning("pointCut()")
    public void afterReturning(){
        System.out.println("afterReturning");
    }

    @AfterThrowing("pointCut()")
    public void afterThrowing(){
        System.out.println("afterThrowing");
    }
}
