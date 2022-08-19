package com.fingerchar.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fingerchar.api.constant.SysConfConstant;


@Configuration
public class FcWebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;
    @Autowired
    private OptionalTokenInterceptor optionalTokenInterceptor;

    private static final String[] NOT_NEED_LOGIN_PATH = {
		SysConfConstant.URL_PREFIX + "/user/getbalance",
		SysConfConstant.URL_PREFIX + "/user/withdraw",
		SysConfConstant.URL_PREFIX + "/user/login",
		SysConfConstant.URL_PREFIX + "/user/reload",
        SysConfConstant.URL_PREFIX + "/system/configs",
		SysConfConstant.URL_PREFIX + "/game/detail",
		SysConfConstant.URL_PREFIX + "/paytoken/list",
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //指定必选token的接口（需要登录）
        registry.addInterceptor(tokenInterceptor)
                .excludePathPatterns(NOT_NEED_LOGIN_PATH);
        //可选token接口（可不登录）
        registry.addInterceptor(optionalTokenInterceptor);
    }
}
