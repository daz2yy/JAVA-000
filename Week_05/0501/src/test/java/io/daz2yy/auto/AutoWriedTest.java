package io.daz2yy.auto;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = AutoWiredBean.class)
public class AutoWriedTest {
    @Autowired
    private AutoWiredBean test;

    @Test
    public void test() {
        test.getInfo();
    }
}
