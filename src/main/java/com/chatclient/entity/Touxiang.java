package com.chatclient.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

//头像表
@Data
public class Touxiang {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String dizhi;
}
