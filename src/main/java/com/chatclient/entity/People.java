package com.chatclient.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

//用户表
@Data
public class People {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String password;
    private Integer age;
    private String mail;
    private String sex;
    private String root;
    private String token;
    private String tou;
    private String createtime;
    public void setCreateTime(String createTime) {
        this.createtime = createTime;
    }
}
