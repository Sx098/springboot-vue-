package com.chatclient;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chatclient.entity.Messageprivate;
import com.chatclient.entity.People;
import com.chatclient.entity.Qun;
import com.chatclient.entity.Shield;
import com.chatclient.mapper.*;
import com.chatclient.vo.FriendsPeopleVo;
import com.chatclient.vo.QunyouPeopleVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChatclientApplicationTests {
    @Autowired
    private PeopleMapper peopleMapper;
    @Autowired
    private FriendsPeopleVoVoMapper voMapper;
    @Autowired
    private ShieldMapper shieldMapper;
    @Autowired
    private QunyouPeopleVoMapper qunyouPeopleVoMapper;
    //和谐
    @Test
    void w(){
        QueryWrapper<Shield> wrapper = new QueryWrapper<>();
        wrapper.select("word");
        List<Shield> shields=shieldMapper.selectList(wrapper);
        List<String> str=new ArrayList<String>();
        for(Shield s:shields){
            str.add(s.getWord());
        }
        System.out.println(str);
        String a="你和谐是不是傻啊";
        for(String ss:str)
        {
            if(a.contains(ss))
            {
                a=a.replace(ss,"***");
            }
        }
        System.out.println(a);
    }
    @Test
    void a(){
        System.out.println(qunyouPeopleVoMapper.sousuo(1));
    }
    //条件查询使用mybatis-plus的wrapper
    @Test
    void cha(){
        QueryWrapper<People> wrapper = new QueryWrapper<>();
        wrapper.like("name","admin");
        List<People> people = peopleMapper.selectList(wrapper);
        for (People p:people){
            System.out.println(p.toString());
        }
    }
    //查询好友，合并列表以及按id升序排列
    @Test
    void haoyou(){
        List<FriendsPeopleVo> people=voMapper.huoqu(1,0);
        List<FriendsPeopleVo> people2=voMapper.huoqu2(1,0);
        people.addAll(people2);
        Collections.sort(people, new Comparator<FriendsPeopleVo>() {
            @Override
            public int compare(FriendsPeopleVo o1, FriendsPeopleVo o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        for(FriendsPeopleVo f:people){
            System.out.println(f);
        }
    }
}
