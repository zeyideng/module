package com.example.spring.test;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class ChildClass1 implements InitializingBean {
    //这里required加上false，是因为是没有建立父子容器关系之前，这个parentClass是注入不了了的
    @Autowired(required = false)
    ParentClass parentClass;

    //这里required加上false，是因为子容器之前是不能够相互引用的，只是测试使用。另注：这个类没有放到这里，在子容器2中，这里不贴代码了
    @Autowired(required = false)
    ChildClass2 childClass2;

    public void print() {
        if (parentClass != null) {
            parentClass.print();
        }
        System.out.println("This is child class 1");
        if (childClass2 != null) {
            childClass2.print();
        }
    }

    public void afterPropertiesSet() throws Exception {
        print();
    }

    public ParentClass getParentClass() {
        return parentClass;
    }
}
