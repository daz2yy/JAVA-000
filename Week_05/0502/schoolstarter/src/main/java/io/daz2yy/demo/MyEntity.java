package io.daz2yy.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "my.entity")
@Data
public class MyEntity {
    private int age;
    private String name;
}
