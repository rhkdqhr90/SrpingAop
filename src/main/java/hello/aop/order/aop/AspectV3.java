package hello.aop.order.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV3 {

    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder(){} //포인트컷ㅎ 시그니쳐

    @Pointcut("execution(* *..*Service.*(..))")
    private void allService(){}

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("[log] {}",joinPoint.getSignature());//join point 시그니쳐
        return joinPoint.proceed();
    }

    //hello.aop.order 패키지와 하위 해키지 면서 클래스 이름 패넡이 *service
    @Around("allOrder() &&allService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable{
        try {
            log.info("트랜잭션 시작 {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("트랜잭션 커밋 {}", joinPoint.getSignature());
            return result;
        }catch(Exception e){
            log.info("트랜잭션 롤백 {}", joinPoint.getSignature());
            throw e;
        }finally{
            log.info("리소스 릴리즈 {} ", joinPoint.getSignature());
        }
    }
}
