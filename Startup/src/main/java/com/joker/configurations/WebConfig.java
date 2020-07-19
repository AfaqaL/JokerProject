package com.joker.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Autowired
    private PasswordRecoveryInterceptor passwordRecoveryInterceptor;

    @Autowired
    private VerifyInterceptor verifyInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/", "/waitingRoom");
        registry.addInterceptor(passwordRecoveryInterceptor).addPathPatterns("/passwordRecovery");
        registry.addInterceptor(verifyInterceptor).addPathPatterns("/verifyCode");
    }
}
