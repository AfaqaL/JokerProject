package com.joker.configurations;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PasswordRecoveryInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req,
            HttpServletResponse resp,
            Object handler) throws Exception {
        Boolean changePassword = (Boolean) req.getSession().getAttribute("changePassword");
        if (changePassword == null || !changePassword) {
            resp.sendRedirect("/forget-password");
            return false;
        }
        return true;
    }
}