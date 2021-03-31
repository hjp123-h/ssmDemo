package com.hjp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: 页面跳转
 * @author: Hjp
 * @time: 2021/3/29 20:05
 */

@Controller
public class PageFromController {


    /**
     *
     * @description: 跳转登录页
     * @author: Hjp
     * @time: 2021/3/29 20:06
     */
    @RequestMapping("/from")
    public String login(){

        return "login";
    }

    /**
     *
     * @description: 跳转注册页
     * @author: Hjp
     * @time: 2021/3/29 20:06
     */
    @RequestMapping("/regist")
    public String regist(){

        return "regist";
    }

    /**
     *
     * @description: 跳转首页
     * @author: Hjp
     * @time: 2021/3/29 23:07
     */
    @RequestMapping("/list")
    public String list(){

        return "userList";
    }

    /**
     *
     * @description: 添加用户跳转
     * @author: Hjp
     * @time: 2021/3/29 23:07
     */
    @RequestMapping("/add")
    public String add(){

        return "userAdd";
    }

    @RequestMapping("/documentation")
    public String documentation(){

        return "documentation";
    }

    @RequestMapping("/documentationAdd")
    public String documentationAdd(){

        return "documentationAdd";
    }
}
