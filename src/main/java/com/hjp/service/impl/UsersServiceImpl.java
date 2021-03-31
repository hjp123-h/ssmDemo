package com.hjp.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjp.bean.Users;
import com.hjp.mapper.UsersMapper;
import com.hjp.service.UsersService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author demo
 * @since 2021-03-28
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

}
