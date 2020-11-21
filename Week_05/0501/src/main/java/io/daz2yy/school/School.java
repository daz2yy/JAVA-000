package io.daz2yy.school;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

@Data
public class School {
    
    @Autowired(required = true)
    Klass class1;
    
    @Resource(name = "student1")
    Student student1;
    
    public School() {
        System.out.println("School Create!");
    }
    
    public void ding(){
        
        System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student1);
        
    }
    
}
