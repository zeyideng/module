package com.excample.module.controller;

import com.excample.module.core.ApplicationContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuezhanfeng
 * @Classname LoadController
 * @Description TODO
 * @Date 2021/5/28 12:01
 */
@RestController
public class LoadController {

    @Autowired
    private ApplicationContextUtil applicationUtil;

    @PostMapping("/get")
    public Object get(@RequestBody Param param) {
        String module = param.getModule();
        Object bean = applicationUtil.getBean(module);
        return bean;
    }

    @PostMapping("/refresh")
    public String refresh(@RequestBody Param param) {
        String module = param.getModule();
        try {
            applicationUtil.createBean(module);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }


    @PostMapping("/destroy")
    public String destroy(@RequestBody Param param) {
        String module = param.getModule();
        try {
            applicationUtil.destroyBean(module);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    @PostMapping("/load")
    public String loadClass(@RequestBody ClassParam param) {
        String module = param.getModule();
        String classPath = param.getClassPath();
        String beanName = param.getBeanName();
        try {
            applicationUtil.loadClass(module, beanName, classPath);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

}
