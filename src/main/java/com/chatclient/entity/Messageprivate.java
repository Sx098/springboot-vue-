package com.chatclient.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
//私聊消息
@Data
public class Messageprivate {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String fromname;
    private String toname;
    private String content;
    private String sendtime;
}
