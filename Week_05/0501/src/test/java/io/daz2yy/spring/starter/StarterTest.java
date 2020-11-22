package io.daz2yy.spring.starter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StarterTest {
    @Autowired
    private io.daz2yy.demo.MySchool school;
    @Test
    public void test() {
        System.out.println(school);
    }
}
