package com.example.spring.container;

import com.example.spring.test.ChildClass1;
import com.example.spring.test.ParentClass;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringModuleContext1 implements ApplicationContextAware {


    private ApplicationContext parent;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(applicationContext.getParent()==null){
            return;
        }
        //Get parent application context
        this.parent = applicationContext.getParent();

        ConfigurableApplicationContext childContext = new ClassPathXmlApplicationContext("classpath:/child1.xml");
        childContext.setParent(this.parent);
        childContext.refresh();
        ParentClass parentClass = childContext.getBean(ParentClass.class);

        System.out.println(parentClass);
        ChildClass1 bean = childContext.getBean(ChildClass1.class);
        System.out.println(bean.getParentClass());
    }


}
