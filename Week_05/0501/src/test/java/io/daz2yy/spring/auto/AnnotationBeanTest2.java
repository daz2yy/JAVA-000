package io.daz2yy.spring.auto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

/**
 * 1. 通过在类上面配置以下两个注解
 * @SpringBootTest ：开启容器？不配置这个会报 nullpoint exception
 * @ContextConfiguration("classpath:annotationBean.xml") ：加载 annotationBean.xml 文件，相当于：
 *      ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("annotationBean.xml");
 *      这样就会把配置文件内的 Bean 创建好了
 * 2. @Resource(name = "annotationBean") ：根据 name 来读取相应的 Bean 对象，因为 Bean 是单例，所以也是唯一的。
 * 3. @Autowried ：默认就是根据变量类型去找对应的 Bean，如果不是单例的话就找不到，
 * 4. 通过 @Scope 来制定 Bean 是否是单例的
 *
 */
@SpringBootTest
@ContextConfiguration("classpath:annotationBean.xml")
public class AnnotationBeanTest2 {
    @Resource(name = "annotationBean")
    private AnnotationBean bean1;
    
    @Autowired
    private AnnotationBean bean2;
    
//    @Qualifier()
//    private AnnotationBean bean3;
    
    @Test
    public void test() {
        bean1.setName("bean1");
        bean1.setAge(111);
        System.out.println(bean1);
    
        bean2.setName("bean2");
        bean2.setAge(222);
        System.out.println(bean2);
    
        // bean1 == bean2
        System.out.println(bean1);
//        bean3.setName("bean3");
//        bean3.setAge(333);
//        System.out.println(bean3);
    }
}
