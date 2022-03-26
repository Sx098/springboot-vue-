package com.chatclient.controller;

import com.chatclient.common.Result;
import com.chatclient.mapper.FriendsPeopleVoVoMapper;
import com.chatclient.mapper.QunyouPeopleVoMapper;
import com.chatclient.vo.FriendsPeopleVo;
import com.chatclient.vo.QunyouPeopleVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@CrossOrigin
@RestController
public class VoController {
    @Resource
    FriendsPeopleVoVoMapper friendsPeopleVoVoMapper;
    @Resource
    QunyouPeopleVoMapper qunyouPeopleVoMapper;
    //获取此用户添加的好友
    @GetMapping("/fp/{id}")
    public Result<List<FriendsPeopleVo>> findfp(@PathVariable Integer id){
        List<FriendsPeopleVo> people=friendsPeopleVoVoMapper.huoqu(id,1);
        List<FriendsPeopleVo> people2=friendsPeopleVoVoMapper.huoqu2(id,1);
        //添加
        people.addAll(people2);
        //按id大小升序排列
        Collections.sort(people, new Comparator<FriendsPeopleVo>() {
            @Override
            public int compare(FriendsPeopleVo o1, FriendsPeopleVo o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        //未同意的请求
        List<FriendsPeopleVo> people3=friendsPeopleVoVoMapper.huoqu(id,0);
        List<FriendsPeopleVo> people4=friendsPeopleVoVoMapper.huoqu2(id,0);
        people3.addAll(people4);
        Collections.sort(people3, new Comparator<FriendsPeopleVo>() {
            @Override
            public int compare(FriendsPeopleVo o1, FriendsPeopleVo o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        people.addAll(people3);
        return Result.success(people);
    }
    //发送添加好友请求
    @PostMapping("/fp")
    public Result<List<FriendsPeopleVo>> addput(
            @RequestParam Integer a,
            @RequestParam Integer b)
    {return Result.success(friendsPeopleVoVoMapper.tianjia(a,b,0));}
    //同意好友请求
    @PutMapping("/fp")
    public Result<Integer> agreeput(
            @RequestParam Integer a,
            @RequestParam Integer b)
    {
        if(friendsPeopleVoVoMapper.tongguo(a,b)==1){return Result.success();}
        else return Result.success(friendsPeopleVoVoMapper.tongguo(b,a));
    }
    @DeleteMapping("/fp")
    public Result<Integer> delno(
            @RequestParam Integer a,
            @RequestParam Integer b)
    {
        if(friendsPeopleVoVoMapper.shan(a,b)==1){return Result.success();}
        else return Result.success(friendsPeopleVoVoMapper.shan(b,a));
    }
    //获取此用户待处理的好友请求
    @GetMapping("/fp/cha/{id}")
    public Result<List<FriendsPeopleVo>> req(@PathVariable Integer id){
        return Result.success(friendsPeopleVoVoMapper.shenqing(id));
    }
    //获取此用户加入的群聊
    @GetMapping("/qp/{id}")
    public Result<List<QunyouPeopleVo>> findqp(@PathVariable Integer id){
        List<QunyouPeopleVo> qun=qunyouPeopleVoMapper.huoqu(id,1);
        List<QunyouPeopleVo> qun2=qunyouPeopleVoMapper.huoqu(id,0);
        qun.addAll(qun2);
        return Result.success(qun);
    }
    //发送添加群聊请求
    @PostMapping("/qp")
    public  Result<List<QunyouPeopleVo>> addput2(
            @RequestParam Integer a,
            @RequestParam Integer b)
    {return Result.success(qunyouPeopleVoMapper.tianjia(a,b,0));}
    //同意加群请求
    @PutMapping("/qp")
    public Result<List<QunyouPeopleVo>> agreeput2(
            @RequestParam Integer a,
            @RequestParam Integer b)
    {return Result.success(qunyouPeopleVoMapper.tongguo(a,b));}
    //获取已加入此群的用户
    @GetMapping("qp/cha/{id}")
    public Result<List<QunyouPeopleVo>> search2(@PathVariable Integer id){
        return Result.success(qunyouPeopleVoMapper.sousuo(id));
    }
}
