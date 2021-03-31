package com.hjp.service;

/**
 * @description: 登录注册逻辑类
 * @author: Hjp
 * @time: 2021/3/29 20:24
 */

public interface LoginService {

    public String login(String username,String password);

    public boolean regist(String username,String password,String phone);
}
