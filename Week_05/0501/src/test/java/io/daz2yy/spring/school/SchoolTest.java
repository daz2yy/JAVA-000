package io.daz2yy.spring.school;

import io.daz2yy.school.School;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * 注意：
 * 1. 不要少了 SprintBootTest，否则报 NullPointException
 */
@SpringBootTest
@ContextConfiguration(locations = "classpath:school.xml")
public class SchoolTest {
    @Autowired
    private School school;
    
    @Test
    public void test() {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("school.xml");
//        school = context.getBean(School.class);
        school.ding();
    }
}
