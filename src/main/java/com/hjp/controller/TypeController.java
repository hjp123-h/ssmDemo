package com.hjp.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hjp.bean.Documentation;
import com.hjp.bean.Type;
import com.hjp.bean.dto.TreeDate;
import com.hjp.bean.dto.TreeKey;
import com.hjp.service.DocumentationService;
import com.hjp.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class TypeController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private DocumentationService documentationService;

    @GetMapping("type")
    @ResponseBody
    public String type(){

        List<Type> list = typeService.list(null);

        ArrayList<TreeDate> dates = new ArrayList<>();
        for (Type t : list) {

            TreeDate date = new TreeDate();
            date.setId(t.getId());
            date.setName(t.getName());
            date.setPId(t.getSuperid());
            if (t.getSuperid() == 0){
                date.setOpen(true);
            }else {
                date.setOpen(null);
            }

            dates.add(date);
        }

        return JSON.toJSONString(dates);
    }

    @PostMapping("/add")
    public String add(@RequestBody TreeKey treeKey, HttpServletRequest httpServletRequest, HttpServletResponse response){

        if (treeKey != null){
            Type type = new Type();
            type.setSuperid(treeKey.getSurpeid());
            type.setName(treeKey.getName());
            type.setCreatetime(new Date());
            type.setModifiedtime(new Date());

            typeService.save(type);
        }
        try {
            response.sendRedirect("/documentation");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "documentation";
    }

    @PostMapping("/update")
    public String update(@RequestBody TreeKey treeKey, HttpServletRequest httpServletRequest, HttpServletResponse response){

        if (treeKey != null){
            Type type = typeService.getById(treeKey.getUserid());

            if (type != null){

                type.setName(treeKey.getName());
                type.setModifiedtime(new Date());
                typeService.updateById(type);
            }
        }
        try {
            response.sendRedirect("/documentation");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "documentation";

    }

    @PostMapping("/deleteType")
    public String deleteType(@RequestBody TreeKey treeKey, HttpServletRequest request, HttpServletResponse response){

        int userid = treeKey.getUserid();

        if (userid != 0 && userid != 1){
            QueryWrapper<Type> wrapper = new QueryWrapper<>();
            wrapper.eq(true,"id",userid)
                    .or()
                    .eq(true,"superid",userid);

            typeService.remove(wrapper);

            QueryWrapper<Documentation> documentationQueryWrapper = new QueryWrapper<>();
            documentationQueryWrapper.eq(true,"typeid",userid);

            documentationService.remove(documentationQueryWrapper);

        }

        try {
            response.sendRedirect("/documentation");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "documentation";

    }
}
