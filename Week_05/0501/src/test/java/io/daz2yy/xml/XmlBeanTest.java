package io.daz2yy.xml;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlBeanTest {
    @Test
    public void test() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("BeanConfig.xml");
        XmlBean bean = (XmlBean) context.getBean("XmlBean");
        bean.getInfo();

    }
}
