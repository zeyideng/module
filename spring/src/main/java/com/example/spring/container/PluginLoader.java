package com.example.spring.container;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PluginLoader implements ApplicationContextAware {

    private ApplicationContext parentApplicationContext;
    private ConfigurableApplicationContext childContext;

    public void load() {
        //扫描所有classpath下面的以plugin_开头spring的配置文件进行装配，这里约定所有的子容器插件都必须有一个以plugin_开头的配置文件，并通过这个文件被父容器加载
        childContext = new ClassPathXmlApplicationContext("classpath*:/plugin_*.xml");
        childContext.setParent(parentApplicationContext);
        childContext.refresh();
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.parentApplicationContext = applicationContext;
    }


}
