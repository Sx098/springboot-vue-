package com.chatclient.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
//好友表与用户表联合
@Data
public class FriendsPeopleVo{
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer frienda;
    private Integer friendb;
    private Integer tongyi;
    private String tou;
}
