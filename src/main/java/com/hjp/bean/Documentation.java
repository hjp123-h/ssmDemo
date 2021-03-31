package com.hjp.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class Documentation {

    @JsonSerialize(using= ToStringSerializer.class)
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    private String title;

    private String content;

    private String file;

    private String typename;

    private String typeid;

    private String username;

    private String userid;

    @TableField(fill = FieldFill.INSERT)
    private Date createtime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifiedtime;
}
