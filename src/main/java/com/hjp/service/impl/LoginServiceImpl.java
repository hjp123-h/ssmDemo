package com.hjp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hjp.bean.Users;
import com.hjp.service.LoginService;
import com.hjp.service.UsersService;
import com.hjp.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Hjp
 * @time: 2021/3/29 20:26
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UsersService usersService;

    /**
     *
     * @description: 登录方法
     * @author: Hjp
     * @time: 2021/3/29 20:27
     */

    @Override
    public String login(String username, String password) {

        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq(true,"name",username);

        Users users = usersService.getOne(wrapper);

        if (users != null){
            //获取数据库中密码
            String usersPassword = users.getPassword();

            //判断MD5加密后密码是否相同
            if (usersPassword.equals(MD5.encrypt(password))){
                return String.valueOf(users.getId());
            }
        }

        return null;
    }

    @Override
    public boolean regist(String username,String password,Long phone) {

        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq(true,"name",username)
                .or(true)
                .eq(true,"phone",phone);

        List<Users> usersList = usersService.list(wrapper);

        if (usersList.isEmpty()){
            Users users = new Users();
            users.setName(username);
            users.setPassword(MD5.encrypt(password));
            users.setPhone(phone);
            users.setAuthority("user");
            users.setCreatetime(new Date());
            users.setModifiedtime(new Date());

            boolean save = usersService.save(users);

            return save;
        }

        return false;
    }
}
