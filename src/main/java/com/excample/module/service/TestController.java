package com.excample.module.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuezhanfeng
 * @Classname TestController
 * @Description TODO
 * @Date 2021/5/28 14:31
 */
@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }





}
