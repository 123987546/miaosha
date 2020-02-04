package com.miaosha.day1.controller;

import com.miaosha.day1.domain.MiaoshaUser;
import com.miaosha.day1.service.GoodsService;
import com.miaosha.day1.service.MiaoshaUserService;
import com.miaosha.day1.service.RedisService;
import com.miaosha.day1.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    MiaoshaUserService miaoshaUserService;
    @Autowired
    RedisService redisService;
    @Autowired
    GoodsService goodsService;

    @RequestMapping("/to_list")
    public String list(Model model,MiaoshaUser user) {
        model.addAttribute("user", user);
        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String list(Model model,MiaoshaUser user, @PathVariable("goodsId")long goodsId) {
        model.addAttribute("user", user);
        GoodsVo goodsVo=goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods",goodsVo);
        long startTime=goodsVo.getStartDate().getTime();
        long endTime=goodsVo.getEndDate().getTime();
        long now=System.currentTimeMillis();
        int miaoshaStatus=0;
        int remianSecond=0;
        if(startTime>now){//未开始
            miaoshaStatus=0;
            remianSecond=(int)((startTime-now)/1000);
        }else if(endTime<now){//已结束
            miaoshaStatus=2;
            remianSecond=-1;
        }else{//正在
            miaoshaStatus=1;
            remianSecond=0;
        }
        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remainSecond",remianSecond);
        return "goods_list";
    }


}
