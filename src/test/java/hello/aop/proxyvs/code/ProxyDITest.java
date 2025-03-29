package hello.aop.proxyvs.code;

import hello.aop.order.member.MemberService;
import hello.aop.order.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest(properties = "spring.aop.proxy-target-class=true")
@Import(ProxyDIAspect.class)
public class ProxyDITest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberServiceImpl memberServiceImpl;

    @Test
    void go(){
      log.info("memberService={}",memberService.getClass());
      log.info("memberServiceImpl={}",memberServiceImpl.getClass());
      memberServiceImpl.hello("hello");
    }
}
