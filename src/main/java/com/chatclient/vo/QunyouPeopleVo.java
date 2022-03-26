package com.chatclient.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
//群友表和用户表联合
@Data
public class QunyouPeopleVo {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer member;
    private Integer group;
    private Integer tongyi;
    private Integer tou;
    private String name;
}
