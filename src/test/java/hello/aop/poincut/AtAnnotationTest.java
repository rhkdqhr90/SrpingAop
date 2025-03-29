package hello.aop.poincut;

import hello.aop.order.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(AtAnnotationTest.ATAnnotationAspect.class)
@SpringBootTest
public class AtAnnotationTest {

    @Autowired
    MemberService memberService;

    @Test
     void success() {
        log.info("memberService proxy={}",memberService.getClass());
        memberService.hello("hello");
    }

    @Slf4j
    @Aspect
    static class ATAnnotationAspect {

        @Around("@annotation(hello.aop.order.member.annotation.MethodAop)")
        public Object doAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@doAnnotation] {}",joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }

}
