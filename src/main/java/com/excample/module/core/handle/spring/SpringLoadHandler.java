package com.excample.module.core.handle.spring;

import com.excample.module.core.ClassObject;
import com.excample.module.core.ObjcetMap;
import com.excample.module.core.handle.LoadHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringLoadHandler implements LoadHandler, ApplicationContextAware {

    @Autowired
    private ObjcetMap objcetMap;

    private ApplicationContext applicationContext;

    @Override
    public Object load(Object param) {
        try {
            createBean(null);
        } catch (Exception e) {
            throw new IllegalArgumentException("请传入正确的类路径");
        }
        return null;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void createBean(String module) throws ClassNotFoundException {
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        ClassObject objClassName = objcetMap.getObjClassName(module);
        if (objClassName == null) {
            return;
        }
        Class<?> aClass = objClassName.getaClass();
        String className = objClassName.getClassName();
        String beanName = objClassName.getBeanName();
        if(aClass == null) {
            aClass = Class.forName(className);
        }
        try {
            defaultListableBeanFactory.getBean(aClass);
        } catch (NoSuchBeanDefinitionException e) {
            e.printStackTrace();
            Object bean = defaultListableBeanFactory.createBean(aClass);
            defaultListableBeanFactory.registerSingleton(beanName, bean);
        }
    }
}
