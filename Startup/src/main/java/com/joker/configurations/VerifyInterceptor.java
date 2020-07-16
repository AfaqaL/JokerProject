package com.joker.configurations;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VerifyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req,
                             HttpServletResponse resp,
                             Object handler) throws Exception {
        Boolean SentCode = (Boolean) req.getSession().getAttribute("sentCode");
        if (SentCode == null || !SentCode) {
            resp.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
