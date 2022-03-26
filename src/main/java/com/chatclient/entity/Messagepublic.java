package com.chatclient.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
//群聊消息
@Data
public class Messagepublic {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String sender;
    private String content;
    private Integer fromgroup;
    private String sendtime;
}
