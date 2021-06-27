package com.example.spring.test;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class ChildClass2 {
    //这里required加上false，是因为是没有建立父子容器关系之前，这个parentClass是注入不了了的
    @Autowired(required = false)
    private ParentClass parentClass;

    //这里required加上false，是因为子容器之前是不能够相互引用的，只是测试使用。另注：这个类没有放到这里，在子容器2中，这里不贴代码了
    @Autowired(required = false)
    ChildClass1 childClass1;

    public void print() {
        if (parentClass != null) {
            parentClass.print();
        }
        System.out.println("This is child class 2");
        if (childClass1 != null) {
            childClass1.print();
        }
    }

    public void afterPropertiesSet() throws Exception {
        print();

    }

    public ParentClass getParentClass() {
        return parentClass;
    }
}
