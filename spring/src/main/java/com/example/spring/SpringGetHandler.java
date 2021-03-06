package com.example.spring;

import com.example.core.ClassObject;
import com.example.core.ObjcetMap;
import com.example.core.handle.GetHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringGetHandler implements GetHandler, ApplicationContextAware {

    @Autowired
    private ObjcetMap objcetMap;

    private ApplicationContext applicationContext;

    @Override
    public Object doGet(String module) {
        return getBean(module);
    }

    public Object getBean(String module) {
        ClassObject objClassName = objcetMap.getObjClassName(module);
        String beanName = objClassName.getBeanName();
        Object bean = applicationContext.getBean(beanName);
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
