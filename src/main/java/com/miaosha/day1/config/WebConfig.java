package com.miaosha.day1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
public class WebConfig implements WebMvcConfigurer {//spring mvc会将ArgumentResolvers注入到controller的参数列表中

    @Autowired
    UserArgumentResolver userArgumentResolver;

    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {//框架会回调这个方法向controller赋参数
        resolvers.add(userArgumentResolver);//将
    }
}
