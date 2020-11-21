package io.daz2yy.spring.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HelloInvokeHandler implements InvocationHandler {
    private Object target;
    
    public HelloInvokeHandler(Object object) {
        this.target = object;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        beforeMethod();
        // 第一个参数不要写成 proxy 了，否则会死循环，想象为什么？
        method.invoke(target, args);
        afterMethod();
        
        return null;
    }
    
    public void beforeMethod() {
        System.out.println("invoke before ==========");
    }
    
    public void afterMethod() {
        System.out.println("invoke after ==========");
    }
}
