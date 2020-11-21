package io.daz2yy.spring.auto;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 相当于 xml注入里配置了一个：<bean id="aaa" class="io.daz2yy.spring.auto.AnnotationBean" />
 *
 * 参考文章：
 * https://www.cnblogs.com/xiaostudy/p/9534697.html
 * https://blog.csdn.net/sinat_37004149/article/details/87915518
 */
@Component
//@Data
public class AnnotationBean {
    private int age;
    private String name;
    
    public AnnotationBean() {
        System.out.println("Annotation Bean Create!");
    }
    
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    
    @Override
    public String toString() {
        return "AnnotationBean, name: " + name + ", age: " + age;
    }
}
