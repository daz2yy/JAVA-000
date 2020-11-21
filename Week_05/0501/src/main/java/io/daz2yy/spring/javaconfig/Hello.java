package io.daz2yy.spring.javaconfig;

import org.springframework.stereotype.Component;

//@Component
public class Hello {
    
    public Hello() {
        System.out.println("Hello is Create!");
    }
    
    public void sayHi() {
        System.out.println("Hello say hi!");
    }
}
