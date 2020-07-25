package com.joker.configurations;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class VerifyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req,
                             HttpServletResponse resp,
                             Object handler) throws Exception {
        Boolean sentCode = (Boolean) req.getSession().getAttribute("sentCode");
        if (sentCode == null || !sentCode) {
            resp.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
