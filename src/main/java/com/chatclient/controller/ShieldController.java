package com.chatclient.controller;

import com.chatclient.common.Result;
import com.chatclient.entity.Shield;
import com.chatclient.mapper.ShieldMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/shield")
public class ShieldController {
    @Resource
    ShieldMapper shieldMapper;
    @GetMapping
    public Result<List<String>> findAll(){return Result.success(shieldMapper.huo());}
    @GetMapping("/find")
    public Result<List<Shield>> find(){return Result.success(shieldMapper.selectList(null));}
    @PostMapping
    public Result add(@RequestBody Shield shield){
        shieldMapper.insert(shield);
        return Result.success();
    }
    @PutMapping
    public Result update(@RequestBody Shield shield){
        shieldMapper.updateById(shield);
        return Result.success();
    }
    @DeleteMapping("{id}")
    public Result delete(@PathVariable Long id){return Result.success(shieldMapper.deleteById(id));}
}
