package com.hjp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hjp.bean.Documentation;
import com.hjp.bean.Type;
import com.hjp.bean.Users;
import com.hjp.bean.dto.PaginationDTO;
import com.hjp.service.DocumentationService;
import com.hjp.service.TypeService;
import com.hjp.service.UsersService;
import com.hjp.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Controller
public class DocumentationController {

    @Autowired
    private DocumentationService documentationService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private TypeService typeService;

    @RequestMapping("/documentationList/{typeId}/{page}")
    public String documentationList(@PathVariable Integer typeId,
                                    @PathVariable Integer page,
                                    @RequestParam(name = "size", defaultValue = "2") Integer size,
                                    HttpServletRequest request,
                                    Model model){
        //当type = 1时 为根目录
        if (typeId == 1){
            typeId = 0;
        }

        PaginationDTO paginationDTO = new PaginationDTO();

        List<Documentation> records =new ArrayList<>();

        Page<Documentation> pageParam = new Page<>(page, size);

        if (typeId != 0){
            QueryWrapper<Documentation> wrapper = new QueryWrapper<>();
            wrapper.eq(true,"typeid",typeId);

            documentationService.page(pageParam,wrapper);
        }else {
            documentationService.page(pageParam,null);
        }

        records = pageParam.getRecords();

        int count = (int) pageParam.getTotal();

        paginationDTO.setPagination(count,page,size);
        paginationDTO.setTypeId(typeId);

        model.addAttribute("pagination",paginationDTO);
        model.addAttribute("data",records);

        return "documentation";
    }

    @RequestMapping("/documentationAdd/{typeId}")
    public String documentationAdd(@PathVariable Integer typeId,
                                   HttpServletRequest request){
        if (typeId != 0){

            //获取登陆者信息
            String id = JwtUtils.getMemberIdByJwtToken(request);

            Users users = usersService.getById(id);

            //获取分类信息
            Type type = typeService.getById(typeId);

            if (users != null && type != null){

                Documentation documentation = new Documentation();

                documentation.setTypename(type.getName());
                documentation.setUsername(users.getName());
                documentation.setCreatetime(new Date());
                documentation.setModifiedtime(new Date());

                documentationService.save(documentation);
            }

        }

        return "forward:/documentationList/"+typeId+"/1";
    }
}
