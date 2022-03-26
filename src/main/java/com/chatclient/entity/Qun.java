package com.chatclient.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


//群表
@Data
public class Qun {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer count;
    private String leader;
    private String createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;
    private String tou;
    private String yanzheng;
}
