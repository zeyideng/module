package com.excample.module.core.handle.springmvc;

import com.excample.module.core.ClassObject;
import com.excample.module.core.ObjcetMap;
import com.excample.module.core.handle.LoadHandler;
import com.excample.module.core.loader.LoadBeanClassLoader;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.mvc.condition.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
public class SpringMVCLoadHandler implements LoadHandler, ServletContextAware {

    @Autowired
    private ObjcetMap objcetMap;

    private  ServletContext servletContext;

    @Autowired
    private AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor;

    @Override
    public Object load(Object param) {
        return null;
    }


    public void loadClass(String module, String beanName, String classPath) throws Exception {
        LoadBeanClassLoader loader = new LoadBeanClassLoader(classPath);
        Class<?> aClass = loader.loadClass(beanName);
        objcetMap.put(module, beanName, aClass);
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        RequestMappingHandlerMapping bean = webApplicationContext.getBean(RequestMappingHandlerMapping.class);
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) webApplicationContext.getAutowireCapableBeanFactory();
        createBean(defaultListableBeanFactory, bean,  module);
    }

    public void createBean(DefaultListableBeanFactory defaultListableBeanFactory,
                           RequestMappingHandlerMapping requestMappingHandlerMapping,  String module) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
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

            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(aClass);
            defaultListableBeanFactory.registerBeanDefinition(aClass.getName(), beanDefinitionBuilder.getBeanDefinition());

            Object bean = defaultListableBeanFactory.createBean(aClass);
            defaultListableBeanFactory.registerSingleton(beanName, bean);

            RequestMappingInfo requestMappingInfo = createRequestMappingInfo(beanName);

            // 找到处理该路由的方法
            Method targetMethod = ReflectionUtils.findMethod(aClass, "hello");

            //注入该类的属性
            autowiredAnnotationBeanPostProcessor.processInjection(bean);

            //反射获取Mapping detectHandlerMethods 方法使Controller 映射
            reflex(requestMappingHandlerMapping, aClass);

            requestMappingHandlerMapping.registerMapping(requestMappingInfo, bean ,targetMethod);
        }
    }

    private RequestMappingInfo createRequestMappingInfo(String beanName) {
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
        return requestMappingInfo;
    }

    public void reflex(RequestMappingHandlerMapping requestMappingHandlerMapping, Class<?> aClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<? extends RequestMappingHandlerMapping> mappingClass = requestMappingHandlerMapping.getClass();
        Class<?> superclass = mappingClass.getSuperclass().getSuperclass();
        Method detectHandlerMethods = superclass.getDeclaredMethod("detectHandlerMethods", Object.class);
        detectHandlerMethods.setAccessible(true);
        detectHandlerMethods.invoke(requestMappingHandlerMapping, aClass.getName());
    }


    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
