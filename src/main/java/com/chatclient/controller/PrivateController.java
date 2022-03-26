package com.chatclient.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chatclient.common.Result;
import com.chatclient.entity.Messageprivate;
import com.chatclient.service.PrivateServicelmpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/private")
public class PrivateController {
    @Resource
    PrivateServicelmpl privateServicelmpl;
    //查询所有消息
    @GetMapping()
    public Result<List<Messageprivate>> findAll(){
        return Result.success(privateServicelmpl.findAll());
    }
    //分页查询
    @GetMapping("/page")
    public Result<Page<Messageprivate>> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name){
    return Result.success(privateServicelmpl.findPage(pageNum,pageSize,name));
    }
    //查找内容
    @GetMapping("/cha/{content}")
    public Result<List<Messageprivate>> findByCon(@PathVariable String content){
        return Result.success(privateServicelmpl.findByCon(content));
    }
    //查找发送者
    @GetMapping("/chafrom/{content}")
    public Result<List<Messageprivate>> findByFrom(@PathVariable String content){
        return Result.success(privateServicelmpl.findByFrom(content));
    }
    //查找接受者
    @GetMapping("/chato/{content}")
    public Result<List<Messageprivate>> findByTo(@PathVariable String content){
        return Result.success(privateServicelmpl.findByTo(content));
    }
    //查询发送接受者
    @GetMapping("/chaft")
    public Result<List<Messageprivate>> findByFT(
            @RequestParam String fromname,
            @RequestParam String toname)
    {
        List<Messageprivate> ft1=privateServicelmpl.findByFT(fromname, toname);
        List<Messageprivate> ft2=privateServicelmpl.findByFT(toname, fromname);
        ft1.addAll(ft2);
        //按时间排序
        Collections.sort(ft1, new Comparator<Messageprivate>() {
            @Override
            public int compare(Messageprivate o1, Messageprivate o2) {
                return o1.getSendtime().compareTo(o2.getSendtime());
            }
        });
        return Result.success(ft1);
    }
}
