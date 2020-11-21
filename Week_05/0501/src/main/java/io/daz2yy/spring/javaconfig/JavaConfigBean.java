package io.daz2yy.spring.javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Configuration ：表明这个类是一个 Spring 的”核心配置“，也就是说，这个类等价于 ==> xmlBean.xml 里的 <beans />
 * @Bean ：表面这个方法是一个 bean 配置，等价于在 xmlBean.xml 种定义了 <bean class="io.daz2yy....." />
 * 注意点：
 * 1. Hello 可以没有 Component 注解，为什么？
 * 2. JavaConfig类 需要通过 AnnotationConfigApplicationContext 来加载，参数是 JavaConfig 的类名
 *      同样的，Configuration 也不是必要的
 * Bean 可以设置 name，这样获取的时候能根据名字来获取，当然也可以用类
 *
 * 参考资料：
 * https://blog.csdn.net/cyan20115/article/details/106551049
 * https://www.cnblogs.com/kaituorensheng/p/8024199.html
 * https://blog.csdn.net/u014199143/article/details/80692685
 * @author daz2yy
 */
@Configuration
public class JavaConfigBean {
    
    @Bean
    public Hello createHello() {
        return new Hello();
    }
}
