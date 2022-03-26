package com.chatclient.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chatclient.entity.Messagepublic;
import com.chatclient.mapper.MessagepublicMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class PublicServicelmpl extends ServiceImpl<MessagepublicMapper, Messagepublic> implements IService<Messagepublic> {
    @Resource
    MessagepublicMapper messagepublicMapper;
    //查询全部消息
    public List<Messagepublic> findAll(){return messagepublicMapper.selectList(null);}
    //分页查询
    public Page<Messagepublic> findPage(Integer pageNum, Integer pageSize, String name){
        Page<Messagepublic> page = new Page<>(pageNum,pageSize);
        page.getRecords();
        return messagepublicMapper.selectPage(page,null);
    }
    //按消息查询
    public List<Messagepublic> findByCon(String content){
        QueryWrapper<Messagepublic> wrapper = new QueryWrapper<>();
        wrapper.like("content",content);
        return messagepublicMapper.selectList(wrapper);
    }
    //查询发送者
    public List<Messagepublic> findBySender(String sender){
        QueryWrapper<Messagepublic> wrapper = new QueryWrapper<>();
        wrapper.like("sender",sender);
        return messagepublicMapper.selectList(wrapper);
    }
    //查询群
    public List<Messagepublic> findByGroup(Integer integer){
        QueryWrapper<Messagepublic> wrapper = new QueryWrapper<>();
        wrapper.like("fromgroup",integer);
        return messagepublicMapper.selectList(wrapper);
    }
}
