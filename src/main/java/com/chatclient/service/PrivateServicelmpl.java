package com.chatclient.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chatclient.entity.Messageprivate;
import com.chatclient.mapper.MessageprivateMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PrivateServicelmpl extends ServiceImpl<MessageprivateMapper, Messageprivate> implements IService<Messageprivate>{
    @Resource
    MessageprivateMapper messageprivateMapper;
    //查询全部消息
    public List<Messageprivate> findAll(){return messageprivateMapper.selectList(null);}
    //分页查询
    public Page<Messageprivate> findPage(Integer pageNum, Integer pageSize, String name){
        Page<Messageprivate> page = new Page<>(pageNum,pageSize);
        page.getRecords();
        return messageprivateMapper.selectPage(page,null);
    }
    //按消息查询
    public List<Messageprivate> findByCon(String content){
        QueryWrapper<Messageprivate> wrapper = new QueryWrapper<>();
        wrapper.like("content",content);
        return messageprivateMapper.selectList(wrapper);
    }
    //查询发送者
    public List<Messageprivate> findByFrom(String fromname){
        QueryWrapper<Messageprivate> wrapper = new QueryWrapper<>();
        wrapper.like("fromname",fromname);
        return messageprivateMapper.selectList(wrapper);
    }
    //查询接受者
    public List<Messageprivate> findByTo(String toname){
        QueryWrapper<Messageprivate> wrapper = new QueryWrapper<>();
        wrapper.like("toname",toname);
        return messageprivateMapper.selectList(wrapper);
    }
    //查询接受者和发送者
    public List<Messageprivate> findByFT(String fromname,String toname){
        QueryWrapper<Messageprivate> wrapper = new QueryWrapper<>();
        wrapper.like("toname",toname).like("fromname",fromname);
        return messageprivateMapper.selectList(wrapper);
    }
}
