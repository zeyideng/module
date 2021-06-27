package com.example.spring.test;

import org.springframework.stereotype.Component;

@Component
public class ParentClass {

    private String username = "张三";

    public void print(){
        System.out.println("This is parent class.");
    }


    @Override
    public String toString() {
        return "ParentClass{" +
                "username='" + username + '\'' +
                '}';
    }
}
