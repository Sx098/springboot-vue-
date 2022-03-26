package com.chatclient.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chatclient.entity.People;
import com.chatclient.mapper.PeopleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
@Service
public class PeopleServiceImpl extends ServiceImpl<PeopleMapper, People> implements IService<People> {
    @Resource
    private PeopleMapper peopleMapper;
    //删除用户
    public void delete(Long id){peopleMapper.deleteById(id);}
    //查询用户id
    public People findById(Long id){return peopleMapper.selectById(id);}
    //查询用户名
    public List<People> findByName(String name){
        HashMap<String,Object> map = new HashMap<>();
        map.put("name",name);
        return peopleMapper.selectByMap(map);
    }
    //条件查询，含有关键字的用户名
    public List<People> findName(String name){
        QueryWrapper<People> wrapper = new QueryWrapper<>();
        wrapper.like("name",name);
        return peopleMapper.selectList(wrapper);
    }
    //查询所有用户
    public List<People> findAll(){return peopleMapper.selectList(null);}
    //分页查询
    public Page<People> findPage(Integer pageNum, Integer pageSize, String name){
        Page<People> page = new Page<>(pageNum,pageSize);
        page.getRecords();
        return peopleMapper.selectPage(page,null);
    }
}
