package com.excample.module.core;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author xuezhanfeng
 * @Classname ExtServletContextListener
 * @Description TODO
 * @Date 2021/5/28 18:54
 */
@Component
public class ServletContextUtil  implements ServletContextAware {

    private  ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }
}
