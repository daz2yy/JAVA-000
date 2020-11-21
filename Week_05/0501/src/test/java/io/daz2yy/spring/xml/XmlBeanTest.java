package io.daz2yy.spring.xml;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlBeanTest {
    @Test
    public void test() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("xmlBean.xml");
        XmlBean bean = (XmlBean) context.getBean("XmlBean");
        bean.getInfo();
    }
}
