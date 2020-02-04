package com.miaosha.day1.controller;

import com.miaosha.day1.domain.MiaoshaUser;
import com.miaosha.day1.service.MiaoshaUserService;
import com.miaosha.day1.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    MiaoshaUserService miaoshaUserService;
    @Autowired
    RedisService redisService;

    @RequestMapping("/to_list")
    public String toList(Model model,MiaoshaUser user){//controller中的参数通过springmvc注入（详情看WebConfig）
        model.addAttribute("user",user);
        return "goods_list";
    }


}
