package com.chatclient.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chatclient.common.Result;
import com.chatclient.entity.Qun;
import com.chatclient.service.QunServicelmpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/qun")
public class QunController {
    @Resource
    private QunServicelmpl qunServicelmpl;
    //添加群组
    @PostMapping
    public Result add(@RequestBody Qun qun){
        qunServicelmpl.save(qun);
        return Result.success();
    }
    //更新群组
    @PutMapping
    public Result update(@RequestBody Qun qun){
        qunServicelmpl.updateById(qun);
        return Result.success();
    }
    //删除群组
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){qunServicelmpl.delete(id);}
    //查找群组id
    @GetMapping("{id}")
    public Result<Qun> findById(@PathVariable Long id){return Result.success(qunServicelmpl.findById(id));}
    //查找群组名
    @GetMapping("/cha/{name}")
    public Result<List<Qun>> findByName(@PathVariable String name){
        return Result.success(qunServicelmpl.findByName(name));
    }
    //查找所有群组
    @GetMapping()
    public Result<List<Qun>> findAll(){
        return Result.success(qunServicelmpl.findAll());
    }
    @GetMapping("/page")
    public Result<Page<Qun>> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name){
        return Result.success(qunServicelmpl.findPage(pageNum, pageSize,name));
    }
}