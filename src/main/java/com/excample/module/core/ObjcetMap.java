package com.excample.module.core;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuezhanfeng
 * @Classname ObjcetMap
 * @Description TODO
 * @Date 2021/5/28 14:19
 */
@Component
public class ObjcetMap {


    private Map<String, ClassObject> objMap = new HashMap<>();


    public  ClassObject getObjClassName(String module) {
        ClassObject list = objMap.get(module);
        return list;
    }

    @PostConstruct
    private void init() {
        ClassObject object = new ClassObject();
        object.setClassName("com.excample.module.service.TestController");
        object.setBeanName("testController");
        this.objMap.put("hello", object);
    }

    public void put(String module, String beanName, Class<?> obj) {
        ClassObject object = new ClassObject();
        object.setaClass(obj);
        object.setClassName(obj.getName());
        object.setBeanName(beanName);
        objMap.put(module, object);
    }


}
