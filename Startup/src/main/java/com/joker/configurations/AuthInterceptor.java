package com.joker.configurations;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req,
                             HttpServletResponse resp,
                             Object handler) throws Exception {
        Boolean authorised = (Boolean) req.getSession().getAttribute("authorised");
        if (authorised == null || !authorised) {
            resp.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
