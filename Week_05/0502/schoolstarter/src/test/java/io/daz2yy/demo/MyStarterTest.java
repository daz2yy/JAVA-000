package io.daz2yy.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyStarterTest {
    @Autowired
    private MyBean bean;
    @Test
    public void test() {
        System.out.println(bean);
    }
}
