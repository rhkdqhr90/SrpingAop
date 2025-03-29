package hello.aop.proxyvs;

import hello.aop.order.member.MemberService;
import hello.aop.order.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

public class ProxyCastingTest {

    @Test
    void jdkProxy(){
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false);//jdk 동적 프록시

        //프록시를 인터페이스로 캐스팅 성공
        MemberService proxy =(MemberService) proxyFactory.getProxy();
        //dk 동적 프록시를 구현 클래스로 캐스팅 시도 실패, ClassCastException 예외 발생
        Assertions.assertThatThrownBy(() -> {MemberServiceImpl castingMemberServiceProxy2 =(MemberServiceImpl) proxyFactory.getProxy();}).isInstanceOf(ClassCastException.class);
    }
    @Test
    void cglibProxy(){
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);//jdk 동적 프록시

        //프록시를 인터페이스로 캐스팅 성공
        MemberService proxy =(MemberService) proxyFactory.getProxy();
        //cglib 프록시를 구현 클래스로 캐시팅 시도 성공
        MemberServiceImpl castingMemberServiceProxy2 =(MemberServiceImpl) proxyFactory.getProxy();


    }
}
