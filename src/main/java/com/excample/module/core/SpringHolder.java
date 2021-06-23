package com.excample.module.core;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpringHolder {

    @Autowired
    private ApplicationContextUtil applicationContextUtil;

    public ApplicationContextUtil getApplicationContextUtil() {
        return applicationContextUtil;
    }

}
