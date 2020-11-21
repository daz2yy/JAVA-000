package io.daz2yy.spring.springconfig;

import io.daz2yy.spring.javaconfig.Hello;
import io.daz2yy.spring.javaconfig.JavaConfigBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class SpringConfigTest {
    
    @Test
    public void test() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfigBean.class);
        System.out.println("========== 分割线 ===========");
        Hello hello = context.getBean(Hello.class);
        hello.sayHi();
    }
}
