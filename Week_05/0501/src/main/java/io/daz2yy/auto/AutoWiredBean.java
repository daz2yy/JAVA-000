package io.daz2yy.auto;

import org.springframework.stereotype.Component;

/**
 * Bean装配 —— 自动注解
 */
@Component
public class AutoWiredBean {

    public AutoWiredBean() {
        System.out.println("AutoWriedBean Contruct!!!");
    }

    public void getInfo() {
        System.out.println("AutoWriedBean =========== info");
    }
}
