package com.chatclient.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chatclient.common.Result;
import com.chatclient.entity.People;
import com.chatclient.mapper.PeopleMapper;
import com.chatclient.service.PeopleServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/people")
public class PeopleController {
    @Resource
    private PeopleServiceImpl peopleService;
    @Resource
    private PeopleMapper peopleMapper;
    //添加用户
    @PostMapping
    public Result add(@RequestBody People people){
        peopleService.save(people);
        return Result.success();
    }
    //更新用户
    @PutMapping
    public Result update(@RequestBody People people){
        peopleService.updateById(people);
        return Result.success();
    }
    //删除用户
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){peopleService.delete(id);}
    //查找用户id
    @GetMapping("{id}")
    public Result<People> findById(@PathVariable Long id){return Result.success(peopleService.findById(id));}
    //查找用户名
    @GetMapping("/cha/{name}")
    public Result<List<People>> findName(@PathVariable String name){return Result.success(peopleService.findName(name));}
    //查询除了当前用户外的其他用户
    @GetMapping("/chap")
    public Result<List<People>> findNamep(
            @RequestParam String na,
            @RequestParam String name)
    {return Result.success(peopleMapper.huoqu(na,name));}
    //查找所有用户
    @GetMapping
    public Result<List<People>> findAll(){
        return Result.success(peopleService.findAll());
    }
    //分页查询
    @GetMapping("/page")
    public Result<Page<People>> findPage(
        @RequestParam(defaultValue = "1") Integer pageNum,
        @RequestParam(defaultValue = "10") Integer pageSize,
        @RequestParam(required = false) String name)
    { return Result.success(peopleService.findPage(pageNum, pageSize,name)); }
    }
