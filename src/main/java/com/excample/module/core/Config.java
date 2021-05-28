package com.excample.module.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author xuezhanfeng
 * @Classname Config
 * @Description TODO
 * @Date 2021/5/28 11:59
 */
@Configuration
public class Config {

    @Autowired
    private ApplicationContextUtil applicationUtil;

    private String module = "demo";



    private void loadBean() {

    }



}
