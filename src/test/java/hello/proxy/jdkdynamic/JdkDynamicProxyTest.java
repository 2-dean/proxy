package hello.proxy.jdkdynamic;

import hello.proxy.jdkdynamic.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {

    @Test
    void dynamicA() {
        AInterface target = new AImpl();

        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        // 이렇게하면 동적으로 프록시 객체가 생성됨
        AInterface proxy = (AInterface)Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler);
        proxy.call();

        log.info("targetClass={}", target.getClass());
        log.info("proxyGetClass={}", proxy.getClass());
    }

    @Test
    void dynamicB() {
        BInterface target = new BImpl();

        TimeInvocationHandler handler = new TimeInvocationHandler(target); // target 넣어줌

        // 이렇게하면 동적으로 프록시 객체가 생성됨
        // 동적프록시는 InvocationHandler.invoke() 호출 // 여기서는  TimeInvocationHandler 가 구현체로있으므로 TimeInvocationHandler.invoke()실행
        BInterface proxy = (BInterface)Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{BInterface.class}, handler);
        proxy.call();

        log.info("targetClass={}", target.getClass());
        log.info("proxyGetClass={}", proxy.getClass());
    }

}
