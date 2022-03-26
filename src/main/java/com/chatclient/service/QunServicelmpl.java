package com.chatclient.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chatclient.entity.Qun;
import com.chatclient.mapper.QunMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
@Service
public class QunServicelmpl extends ServiceImpl<QunMapper, Qun> implements IService<Qun> {
    @Resource
    QunMapper qunMapper;
    //删除群
    public void delete(Long id) {
        qunMapper.deleteById(id);
    }
    //查询群id
    public Qun findById(Long id){return qunMapper.selectById(id);}
    //条件查询，含有关键字的群名
    public List<Qun> findByName(String name){
        QueryWrapper<Qun> wrapper = new QueryWrapper<>();
        wrapper.like("name",name);
        return qunMapper.selectList(wrapper);
    }
    //查询所有群
    public List<Qun> findAll(){return qunMapper.selectList(null);}
    //分页查询
    public Page<Qun> findPage(Integer pageNum, Integer pageSize, String name)
    {
        Page<Qun> page = new Page<>(pageNum,pageSize);
        page.getRecords();
        return qunMapper.selectPage(page,null);
    }
}
