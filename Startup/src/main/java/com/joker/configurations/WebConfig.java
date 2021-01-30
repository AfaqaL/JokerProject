package com.joker.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    private final PasswordRecoveryInterceptor passwordRecoveryInterceptor;

    private final VerifyInterceptor verifyInterceptor;

    public WebConfig(AuthInterceptor authInterceptor, PasswordRecoveryInterceptor passwordRecoveryInterceptor, VerifyInterceptor verifyInterceptor) {
        this.authInterceptor = authInterceptor;
        this.passwordRecoveryInterceptor = passwordRecoveryInterceptor;
        this.verifyInterceptor = verifyInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/", "/waitingRoom");
        registry.addInterceptor(passwordRecoveryInterceptor).addPathPatterns("/passwordRecovery");
        registry.addInterceptor(verifyInterceptor).addPathPatterns("/verifyCode");
    }

}
