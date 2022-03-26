package com.chatclient.controller;

import com.chatclient.common.Result;
import com.chatclient.entity.Touxiang;
import com.chatclient.mapper.TouxiangMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/touxiang")
public class TouxiangController {
    @Resource
    private TouxiangMapper touxiangMapper;
    //获取头像
    @GetMapping
    public Result<List<Touxiang>> findAll(){return Result.success(touxiangMapper.selectList(null));}
}
