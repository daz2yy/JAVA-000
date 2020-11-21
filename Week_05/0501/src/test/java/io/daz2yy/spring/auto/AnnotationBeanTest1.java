package io.daz2yy.spring.auto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

public class AnnotationBeanTest1 {
    
    /**
     * 正常创建的方式
     */
    @Test
    public void test0() {
        AnnotationBean bean0 = new AnnotationBean();
        bean0.setName("bean0");
        bean0.setAge(000);
        System.out.println(bean0);
    }
    
    /**
     * 1. Bean 默认创建方式是 Singleton 的，所以在加载 context 的时候就会看到构造函数的语句打印了
     * 2. getBean 可以通过类名来获取对应的 Bean 对象
     * 3. getBean 可以通过类名的第一个字母小写来获取！（没找到哪里有说明，但是看到有这样的例子）
     */
    @Test
    public void test1() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("annotationBean.xml");
        AnnotationBean bean1 = context.getBean(AnnotationBean.class);
        bean1.setName("bean1");
        bean1.setAge(111);
        System.out.println(bean1);
    
        // 因为是单例，所以 age 还是111
        AnnotationBean bean2 = context.getBean("annotationBean", AnnotationBean.class);
        bean2.setName("bean2");
        System.out.println(bean2);
    }
    
}
