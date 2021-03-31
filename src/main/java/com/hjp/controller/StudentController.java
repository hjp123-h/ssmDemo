package com.hjp.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hjp.bean.Student;
import com.hjp.bean.Users;
import com.hjp.bean.dto.PaginationDTO;
import com.hjp.service.StudentService;
import com.hjp.service.UsersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: user类
 * @author: Hjp
 * @time: 2021/3/29 22:57
 */

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public String students(@RequestParam(name = "page", defaultValue = "1") Integer page,
                           @RequestParam(name = "size", defaultValue = "2") Integer size,
                           @RequestParam(name = "phone", defaultValue = "0") Integer phone,
                           Model model){

        PaginationDTO paginationDTO = new PaginationDTO();

        List<Student> records =new ArrayList<>();

        Page<Student> pageParam = new Page<>(page, size);

        if (phone == 0){

            studentService.page(pageParam,null);

        }else {

            QueryWrapper<Student> wrapper = new QueryWrapper<>();
            wrapper.eq(true,"phone",phone);
            studentService.page(pageParam,wrapper);

        }

        records = pageParam.getRecords();

        int count = (int) pageParam.getTotal();

        paginationDTO.setPagination(count,page,size);

        model.addAttribute("pagination",paginationDTO);
        model.addAttribute("data",records);

        return "userList";
    }

    @PostMapping("/addStudent")
    @ResponseBody
    public String addStudent(@RequestBody Student student){

        if (student != null){
            student.setCreatetime(new Date());
            student.setModifiedtime(new Date());
            boolean save = studentService.save(student);

            if (save){
                return JSON.toJSONString("true");
            }
        }

        return JSON.toJSONString("false");
    }

    @GetMapping("/getByIdStudent/{phones}")
    public String getByIdStudent(@PathVariable String phones, Model model, HttpServletResponse response){

        if (StringUtils.isNotBlank(phones) && !phones.equals("students")){

            QueryWrapper<Student> wrapper = new QueryWrapper<>();
            wrapper.like(true,"phone",phones);

            List<Student> list = studentService.list(wrapper);

            if (!list.isEmpty()){
                model.addAttribute("data",list);
            }
        }else {
            try {
                response.sendRedirect("/students");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "userList";
    }

    @GetMapping("/getById/{id}")
    @ResponseBody
    public String getById(@PathVariable int id){

        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq(true,"id",id);

        Student students = studentService.getOne(wrapper);

        return JSON.toJSONString(students);

    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public String update(@PathVariable int id,@RequestBody Student student){

        if (id != 0 && student != null){
            student.setId(id);
            student.setModifiedtime(new Date());

            boolean update = studentService.updateById(student);

            if (update){
                return JSON.toJSONString("true");
            }
        }

        return JSON.toJSONString("false");

    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable int id){
        boolean remove = studentService.removeById(id);

        if (remove){
            return JSON.toJSONString("true");
        }
        return JSON.toJSONString("false");
    }
}
