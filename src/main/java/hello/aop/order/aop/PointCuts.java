package hello.aop.order.aop;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {
    //hello.aop.order 패키지와 그 하위 패키지
    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder(){} //포인트컷ㅎ 시그니쳐
    // 클래ㅑ스 이름 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    //allOrder && allService
    @Pointcut("allOrder() && allService()")
    public void orderAndService(){ }
}
