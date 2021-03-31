package com.hjp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hjp.bean.Users;
import com.hjp.service.LoginService;
import com.hjp.util.JwtUtils;
import io.jsonwebtoken.Jwt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @description: 登录注册
 * @author: Hjp
 * @time: 2021/3/29 20:06
 */
@Controller
public class LoginController {

    @Autowired
    public LoginService loginService;

    /**
     *
     * @description: 登录方法
     * @author: Hjp
     * @time: 2021/3/29 22:59
     */

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response){
        String userName = request.getParameter("formName");
        String password = request.getParameter("password");

        //判断是否为空
        if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)){
            String userID = loginService.login(userName, password);

            if (StringUtils.isNotBlank(userID)){

                //生成token
                String token = JwtUtils.getJwtToken(userID, userName);

                Cookie cookie = new Cookie("token", token);
                response.addCookie(cookie);

                try {
                    response.sendRedirect("/students");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return "login";
    }

    /**
     *
     * @description: 注册方法
     * @author: Hjp
     * @time: 2021/3/29 22:59
     */

    @RequestMapping("/register")
    public String register(HttpServletRequest request, HttpServletResponse response){

        String userName = request.getParameter("formName");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");

        if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password) && StringUtils.isNotBlank(phone)){
            boolean regist = loginService.regist(userName, password, phone);

            if (regist){

                try {
                    response.sendRedirect("/login");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "login";
            }
        }

        try {
            response.sendRedirect("/regist");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "login";
    }
}
