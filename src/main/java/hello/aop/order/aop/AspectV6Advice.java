package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

    @Around("hello.aop.order.aop.PointCuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature()); //join point 시그니처
            Object result = joinPoint.proceed();
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature()); //join point 시그니처
            return null;
        } catch (Exception e) {
            //@AfterThrowing
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Before("hello.aop.order.aop.PointCuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] : {}", joinPoint.getSignature());
    }

    @AfterReturning(value = "hello.aop.order.aop.PointCuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint,  Object result) {
        log.info("[return] : {}, return = {}", joinPoint.getSignature(), result);
    }

    @AfterReturning(value = "hello.aop.order.aop.PointCuts.allOrder()", returning = "result")
    public void doReturn(JoinPoint joinPoint,  String result) {
        log.info("[return2] : {}, return = {}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "hello.aop.order.aop.PointCuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint,  Exception ex) {
        log.info("[ex] : {}", joinPoint.getSignature());
    }

    @After(value = "hello.aop.order.aop.PointCuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }
}
