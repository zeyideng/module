package com.excample.module.core.handle.spring;

import com.excample.module.core.ClassObject;
import com.excample.module.core.ObjcetMap;
import com.excample.module.core.handle.GetHandler;
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
