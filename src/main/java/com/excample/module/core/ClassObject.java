package com.excample.module.core;

/**
 * @author xuezhanfeng
 * @Classname ClassMap
 * @Description TODO
 * @Date 2021/5/28 18:18
 */
public class ClassObject {

    private String beanName;

    private String className;

    private Class<?> aClass;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Class<?> getaClass() {
        return aClass;
    }

    public void setaClass(Class<?> aClass) {
        this.aClass = aClass;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
