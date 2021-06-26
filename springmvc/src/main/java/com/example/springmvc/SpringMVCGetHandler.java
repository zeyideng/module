package com.example.springmvc;

import com.example.core.handle.GetHandler;
import org.springframework.stereotype.Component;

@Component
public class SpringMVCGetHandler implements GetHandler {


    @Override
    public Object doGet(String module) {
        return null;
    }


}
