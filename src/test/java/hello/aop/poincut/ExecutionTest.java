package hello.aop.poincut;

import hello.aop.order.member.MemberService;
import hello.aop.order.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberService.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod(){
        //public abstract java.lang.String hello.aop.order.member.MemberService.hello(java.lang.String)
        log.info("helloMethod={}",helloMethod);
    }

    @Test
    void exactMatch(){
        pointcut.setExpression("execution(public String hello.aop.order.member.MemberService.hello(String))");
        assertThat(pointcut.matches(helloMethod, MemberService.class)).isTrue();
    }

    @Test
    void allMatch(){
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberService.class)).isTrue();
    }
    @Test
    void nameMatch(){
    pointcut.setExpression("execution(* hello(..))");
    assertThat(pointcut.matches(helloMethod, MemberService.class)).isTrue();
    }

    @Test
    void NameMatchFail(){
        pointcut.setExpression("execution(* nono(String))");
        assertThat(pointcut.matches(helloMethod, MemberService.class)).isFalse();
    }
    @Test
    void packageExactMatch1(){
        pointcut.setExpression("execution(* hello.aop.order.member.MemberService.hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberService.class)).isTrue();
    }
    @Test
    void packageExactMatch2(){
        pointcut.setExpression("execution(* hello.aop.order.member.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberService.class)).isTrue();
    }

    @Test
    void packageExactMatch3(){
        pointcut.setExpression("execution(* hello.aop.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberService.class)).isTrue();
    }

    @Test
    void packageMatchSubPackage(){
        pointcut.setExpression("execution(* hello.aop..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberService.class)).isTrue();
    }
    @Test
    void packageMatchSubPackage2(){
        pointcut.setExpression("execution(* hello.aop.order.member..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeExactMatch(){
        pointcut.setExpression("execution(* hello.aop.order.member.MemberServiceImpl.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }


    @Test
    void typeExactMatch2(){
        pointcut.setExpression("execution(* hello.aop.order.member.MemberService.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeExactNoSuperTypeMethodFalse() throws NoSuchMethodException {
        pointcut.setExpression("execution(* hello.aop.order.member.MemberService.*(..))");
        Method internal = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internal, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void typeExactInternal() throws NoSuchMethodException {
        pointcut.setExpression("execution(* hello.aop.order.member.MemberServiceImpl.*(..))");
        Method internal = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internal, MemberServiceImpl.class)).isTrue();
    }
    //String 타입의 파라미터 허용
    @Test
    void argsMath() throws NoSuchMethodException {
        pointcut.setExpression("execution(* *(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    //파라미터가 없어야함
    @Test
    void argsMathFalse() throws NoSuchMethodException {
        pointcut.setExpression("execution(* *())");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }
    //정확히 하나의 파라미터 허용, 모든 타입 허용
    @Test
    void argsMathStar() throws NoSuchMethodException {
        pointcut.setExpression("execution(* *(*))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    // 숫자와 무관하게 모든 파라미터 모든타입허용
    //(),(Xxx),(Xxx,Xxx)
    @Test
    void argsMathAll() throws NoSuchMethodException {
        pointcut.setExpression("execution(* *(*))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    //String 타입으로 시작, 숫자와 무관하게 모든 파라미터 허용, 모든 타입 허용
    //(String),(String,Xxx),(String,Xxx,Xxx)
    @Test
    void argsMathComplex() throws NoSuchMethodException {
        pointcut.setExpression("execution(* *(String, ..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }


}
