package com.chatclient.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Shield {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String word;
    private String addtime;
}
