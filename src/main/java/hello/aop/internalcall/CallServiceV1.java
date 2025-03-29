package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

    private  CallServiceV1 callServiceV1;

    @Autowired
    public void setCallServiceV1(@Lazy CallServiceV1 callServiceV1) {
        this.callServiceV1 = callServiceV1;
    }

    public void external(){
        log.info("call external");
        callServiceV1.internal();
    }

    public void internal(){
        log.info("call internal");
    }
}
