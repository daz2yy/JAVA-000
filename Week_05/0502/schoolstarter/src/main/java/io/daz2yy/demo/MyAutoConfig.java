package io.daz2yy.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MyEntity.class)
public class MyAutoConfig {
    @Autowired
    MyEntity entity;
    @Bean
    public MyBean createBean() {
        System.out.println("CreateBean !!!!");
        return new MyBean(entity.getAge(), entity.getName());
    }
}
