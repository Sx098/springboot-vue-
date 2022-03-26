package com.chatclient.util;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    //插入时的填充策略
    @Override
    public void insertFill(MetaObject metaObject) {

    }
    //更新时的填充策略
    @Override
    public void updateFill(MetaObject metaObject) {
        //自动填充，当前时间
        //date转换为string类型
        SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");//日期格式
        String tiem = sformat.format(new Date());
        this.setFieldValByName("updateTime",tiem,metaObject);
    }
}
