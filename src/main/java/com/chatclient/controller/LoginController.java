package com.chatclient.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chatclient.common.Result;
import com.chatclient.entity.People;
import com.chatclient.service.PeopleServiceImpl;
import com.chatclient.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@CrossOrigin
@RestController
public class LoginController {
    @Resource
    PeopleServiceImpl peopleService;
    @GetMapping("/log")
    public People login(People people, HttpSession session){
        QueryWrapper<People> wrapper = new QueryWrapper<>();
        wrapper.eq("name",people.getName()).eq("password",people.getPassword());
        if(peopleService.getOne(wrapper)!=null){
            people.setToken(JwtUtil.createToken());
            //像session里设置name
            session.setAttribute("name",people.getName());
            return people;
        }
        return null;
    }
    @GetMapping("/checkToken")
    public List<People> checkToken(People people){
        String token = people.getToken();
        if(JwtUtil.checkToken(token)){
            String name = people.getName();
            return peopleService.findByName(name);
        }
        return null;
    }
    @PostMapping("/log")
    public Result add(@RequestBody People people){
        peopleService.save(people);
        return Result.success();
    }
    @GetMapping("/getUsername")
    @ResponseBody
    public String getUsername(HttpSession httpSession){return (String) httpSession.getAttribute("name");}
}
