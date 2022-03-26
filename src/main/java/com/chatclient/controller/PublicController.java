package com.chatclient.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chatclient.common.Result;
import com.chatclient.entity.Messagepublic;
import com.chatclient.service.PublicServicelmpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/public")
public class PublicController {
    @Resource
    PublicServicelmpl publicServicelmpl;
    //查询所有消息
    @GetMapping()
    public Result<List<Messagepublic>> findAll(){return Result.success(publicServicelmpl.findAll());}
    //分页查询
    @GetMapping("/page")
    public Result<Page<Messagepublic>> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name){
        return Result.success(publicServicelmpl.findPage(pageNum,pageSize,name));
    }
    //查找内容
    @GetMapping("/cha/{content}")
    public Result<List<Messagepublic>> findByCon(@PathVariable String content){
        return Result.success(publicServicelmpl.findByCon(content));
    }
    //查询发送者
    @GetMapping("/chafrom/{content}")
    public Result<List<Messagepublic>> findBySender(@PathVariable String content){
        return Result.success(publicServicelmpl.findBySender(content));
    }
    //查询群
    @GetMapping("/chato/{integer}")
    public Result<List<Messagepublic>> findByGroup(@PathVariable Integer integer){
        return Result.success(publicServicelmpl.findByGroup(integer));
    }
}
