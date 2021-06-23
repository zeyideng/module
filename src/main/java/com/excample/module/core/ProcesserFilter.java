package com.excample.module.core;

import java.util.HashMap;
import java.util.Map;

public class ProcesserFilter implements Filter<RequestProcessor> {

    private Map<String, RequestProcessor> processorMap  = new HashMap<>();

    @Override
    public void addFilter(String key , RequestProcessor param) {
        processorMap.put(key, param);
    }

    @Override
    public RequestProcessor getFilter(String key) {
        return processorMap.get(key);
    }

}
