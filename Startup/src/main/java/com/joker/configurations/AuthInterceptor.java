package com.joker.configurations;

import com.joker.model.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req,
                             HttpServletResponse resp,
                             Object handler) throws Exception {
        Boolean Authorised = (Boolean) req.getSession().getAttribute("authorised");
        if (Authorised == null || !Authorised) {
            resp.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
