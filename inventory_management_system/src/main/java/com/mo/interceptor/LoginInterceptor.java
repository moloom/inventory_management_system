package com.mo.interceptor;

import com.mo.pojo.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String requestURI = request.getRequestURI();
        System.out.println("preHandle拦截的请求路径是{}" + request.getRequestURL());
        Employee employee = (Employee) session.getAttribute("employeeSession");
        if (employee != null) return true;
        request.setAttribute("msg", "请登录账号！");
        session.removeAttribute("mInOutRepositoryBid");
        session.removeAttribute("pInOutRepositoryBid");
        request.getRequestDispatcher("/login").forward(request, response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
