package com.lixuan.service.base.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import sun.util.resources.LocaleData;

import java.util.Date;

/**
 * 自动填充处理类
 * @author LiXxuan
 * @date 2020/9/14 16:00
 */
@Component
public class CommonMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName("gmtCreate", new Date(),metaObject);
        setFieldValByName("gmtModified",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("gmtModified",new Date(),metaObject);
    }
}
