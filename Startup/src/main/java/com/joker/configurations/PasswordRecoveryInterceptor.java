package com.joker.configurations;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PasswordRecoveryInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req,
            HttpServletResponse resp,
            Object handler) throws Exception {
        Boolean ChangePassword = (Boolean) req.getSession().getAttribute("changePassword");
        if (ChangePassword == null || !ChangePassword) {
            resp.sendRedirect("/forgetPassword");
            return false;
        }
        return true;
    }
}