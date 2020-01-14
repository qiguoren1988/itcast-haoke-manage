package com.mmall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author solang
 * @date 2020-01-08 0:17
 */
@Configuration
class JsonViewConfig {
    @Bean
    public MappingJackson2JsonView mappingJackson2JsonView(){
        return new MappingJackson2JsonView();
    }

    @Bean
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver(){
        ContentNegotiatingViewResolver cnvr = new ContentNegotiatingViewResolver();
        List<View> list = new ArrayList();
        list.add(mappingJackson2JsonView());
        cnvr.setDefaultViews(list);
        return cnvr;
    }
}
