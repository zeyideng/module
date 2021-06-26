package com.example.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuezhanfeng
 * @Classname Config
 * @Description TODO
 * @Date 2021/5/28 11:59
 */
@Configuration
public class Config {

    private boolean http = true;
    private boolean rpc = true;
    private boolean commline = true;

    public static final String HTTP_MAP = "http";
    public static final String RPC_MAP = "rpc";
    public static final String COMMLINE_MAP = "commline";


    @Bean
    public ProcesserFilter init() {
        ProcesserFilter filter = new ProcesserFilter();
        if(http) {
            HttpRequestProcessor httpRequestProcessor = new HttpRequestProcessor();
            filter.addFilter(HTTP_MAP, httpRequestProcessor);
        }

        if(rpc) {
            RpcRequestProcesser rpcRequestProcesser = new RpcRequestProcesser();
            filter.addFilter(RPC_MAP, rpcRequestProcesser);
        }

        if(commline) {
            CommonLineRequestProcesser commonLineRequestProcesser = new CommonLineRequestProcesser();
            filter.addFilter(COMMLINE_MAP, commonLineRequestProcesser);
        }
        return filter;
    }



}
