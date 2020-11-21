package io.daz2yy.spring.proxy;

public class HelloImpl implements IHello {
    @Override
    public void sayHello() {
        System.out.println("Hello Implement say hello!");
    }
}
