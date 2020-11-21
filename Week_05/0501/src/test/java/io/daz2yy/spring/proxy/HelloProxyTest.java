package io.daz2yy.spring.proxy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

@SpringBootTest
public class HelloProxyTest {
    @Test
    public void test() {
        HelloImpl hello = new HelloImpl();
        InvocationHandler handler = new HelloInvokeHandler(hello);
        
        ClassLoader loader = hello.getClass().getClassLoader();
        Class[] interfaces = hello.getClass().getInterfaces();
        IHello proxy = (IHello) Proxy.newProxyInstance(loader, interfaces, handler);
        
        proxy.sayHello();
    }
}
