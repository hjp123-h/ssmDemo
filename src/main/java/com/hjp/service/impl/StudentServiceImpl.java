package com.hjp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjp.bean.Student;
import com.hjp.bean.Users;
import com.hjp.mapper.StudentMapper;
import com.hjp.mapper.UsersMapper;
import com.hjp.service.StudentService;
import com.hjp.service.UsersService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
}
