package com.miaosha.day1.controller;

import com.miaosha.day1.domain.User;
import com.miaosha.day1.redis.UserKey;
import com.miaosha.day1.result.CodeMsg;
import com.miaosha.day1.result.Result;
import com.miaosha.day1.service.RedisService;
import com.miaosha.day1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
        //return new Result(0,"success","hello_1");
    }


    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet(){
        User u=userService.getById(1);
        return Result.success(u);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbtx(){
        userService.tx();
        return Result.success(true);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet(){
        User user=redisService.get(UserKey.getById,""+1,User.class);
        return Result.success(user);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet(){
        User user=new User(1,"11111");
        boolean b=redisService.set(UserKey.getById,""+1,user);
        return Result.success(true);
    }
}
