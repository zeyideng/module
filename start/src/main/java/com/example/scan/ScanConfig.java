package com.example.scan;

import com.example.spring.container.PluginLoader;
import com.example.spring.container.SpringModuleContext1;
import com.example.spring.container.SpringModuleContext2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScanConfig {

    @Bean(initMethod = "load")
    public PluginLoader getPluginLoader() {
        PluginLoader pluginLoader = new PluginLoader();
        return pluginLoader;
    }




    //@Bean
    public SpringModuleContext1 getSpringModuleContext1() {
        SpringModuleContext1 pluginLoader = new SpringModuleContext1();
        return pluginLoader;
    }


    //@Bean
    public SpringModuleContext2 getSpringModuleContext2() {
        SpringModuleContext2 pluginLoader = new SpringModuleContext2();
        return pluginLoader;
    }
}
