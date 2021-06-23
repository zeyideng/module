package com.excample.module.core.handle.spring;

import com.excample.module.core.ClassObject;
import com.excample.module.core.ObjcetMap;
import com.excample.module.core.handle.DestroyHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringDestroyHandler implements DestroyHandler, ApplicationContextAware {

    @Autowired
    private ObjcetMap objcetMap;

    private ApplicationContext applicationContext;

    @Override
    public void destroy(String module) {
        destroyBean(module);
    }


    public void destroyBean(String module) {
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        ClassObject objClassName = objcetMap.getObjClassName(module);
        if (objClassName == null) {
            return;
        }
        String beanName = objClassName.getBeanName();
        defaultListableBeanFactory.removeBeanDefinition(beanName);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
