package com.hjp.interceptor;

import com.hjp.service.UsersService;
import com.hjp.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 设置登陆拦截器
 */

@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取前端cookie
        Cookie[] cookies = request.getCookies();
        //判断cookies是否为空
        if (cookies != null){
            //遍历这个cookie
            for (Cookie cookie:cookies) {
                if (cookie.getName().equals("token")){
                    //获取这个cookie中的id
                    String value = cookie.getValue();

                    boolean checkToken = JwtUtils.checkToken(value);

                    if (checkToken){
                        return true;
                    }
                }

            }
        }

        request.getRequestDispatcher("/html/login.html").forward(request, response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
