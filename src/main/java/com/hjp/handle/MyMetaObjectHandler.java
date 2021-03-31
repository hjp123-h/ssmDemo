package com.hjp.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description: MyBatis-Plus自动填充类
 * @author: Hjp
 * @time: 2020/7/2 16:39
 */

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createtime", new Date(), metaObject);
        this.setFieldValByName("modifiedtime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("modifiedtime", new Date(), metaObject);
    }
}
