package com.excample.module.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.mvc.condition.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;

/**
 * @author xuezhanfeng
 * @Classname ApplicationUtil
 * @Description TODO
 * @Date 2021/5/28 13:49
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private ServletContextUtil servletContextUtil;

    @Autowired
    private AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor;

    @Autowired
    private ObjcetMap objcetMap;

    public ApplicationContext getApplicationContext() {
        return applicationContext;
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


    public void destroyBean(String module) {
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        ClassObject objClassName = objcetMap.getObjClassName(module);
        if (objClassName == null) {
            return;
        }
        String beanName = objClassName.getBeanName();
        defaultListableBeanFactory.removeBeanDefinition(beanName);
    }

    public Object getBean(String module) {
        ClassObject objClassName = objcetMap.getObjClassName(module);
        String beanName = objClassName.getBeanName();
        Object bean = applicationContext.getBean(beanName);
        return bean;
    }

    public void loadClass(String module, String beanName, String classPath) throws Exception {
        LoadBeanClassLoader loader = new LoadBeanClassLoader(classPath);
        Class<?> aClass = loader.loadClass(beanName);
        objcetMap.put(module, beanName, aClass);
        ServletContext servletContext = servletContextUtil.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        RequestMappingHandlerMapping bean = webApplicationContext.getBean(RequestMappingHandlerMapping.class);
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) webApplicationContext.getAutowireCapableBeanFactory();
        createBean(defaultListableBeanFactory, bean,  module);
    }

    public void createBean(DefaultListableBeanFactory defaultListableBeanFactory, RequestMappingHandlerMapping requestMappingHandlerMapping,  String module) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
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
            // 找到处理该路由的方法
            Method targetMethod = ReflectionUtils.findMethod(aClass, "hello");
            PatternsRequestCondition patternsRequestCondition = new PatternsRequestCondition("/test");
            RequestMethodsRequestCondition requestMethodsRequestCondition = new RequestMethodsRequestCondition();
            ParamsRequestCondition paramsRequestCondition = new ParamsRequestCondition();
            HeadersRequestCondition headersRequestCondition = new HeadersRequestCondition();
            ConsumesRequestCondition consumesRequestCondition = new ConsumesRequestCondition();
            ProducesRequestCondition producesRequestCondition = new ProducesRequestCondition();
            MediaTypeExpression mediaTypeExpression = new MediaTypeExpression() {
                @Override
                public MediaType getMediaType() {
                    return MediaType.APPLICATION_JSON_UTF8;
                }
                @Override
                public boolean isNegated() {
                    return false;
                }
            };
            producesRequestCondition.getExpressions().add(mediaTypeExpression);
            RequestMappingInfo requestMappingInfo = new RequestMappingInfo(beanName, patternsRequestCondition, requestMethodsRequestCondition, paramsRequestCondition, headersRequestCondition, consumesRequestCondition, producesRequestCondition, null);
            autowiredAnnotationBeanPostProcessor.processInjection(bean);

            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(aClass);
            defaultListableBeanFactory.registerBeanDefinition(aClass.getName(), beanDefinitionBuilder.getBeanDefinition());

            Class<? extends RequestMappingHandlerMapping> mappingClass = requestMappingHandlerMapping.getClass();
            Class<?> superclass = mappingClass.getSuperclass().getSuperclass();
            Method detectHandlerMethods = superclass.getDeclaredMethod("detectHandlerMethods", Object.class);
            detectHandlerMethods.setAccessible(true);
            detectHandlerMethods.invoke(requestMappingHandlerMapping, aClass.getName());
            requestMappingHandlerMapping.registerMapping(requestMappingInfo, bean ,targetMethod);
        }
    }

}
