package com.miaosha.day1.controller;

import com.miaosha.day1.domain.MiaoshaUser;
import com.miaosha.day1.domain.OrderInfo;
import com.miaosha.day1.result.CodeMsg;
import com.miaosha.day1.result.Result;
import com.miaosha.day1.service.GoodsService;
import com.miaosha.day1.service.MiaoshaUserService;
import com.miaosha.day1.service.OrderService;
import com.miaosha.day1.service.RedisService;
import com.miaosha.day1.vo.GoodsVo;
import com.miaosha.day1.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    MiaoshaUserService miaoshaUserService;
    @Autowired
    RedisService redisService;
    @Autowired
    OrderService orderService;
    @Autowired
    GoodsService goodsService;


    @RequestMapping(value="/detail",produces = "text/html")
    @ResponseBody
    public Result<OrderVo> detail(@RequestParam("orderId")long orderId, MiaoshaUser user, Model model) {
        if(user==null){
            return Result.error(CodeMsg.SERVER_ERROR);
        }
        OrderInfo orderInfo=orderService.getOrderById(orderId);
        if(orderInfo==null){
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        OrderVo order=new OrderVo();
        long goodsId=orderInfo.getGoodsId();
        GoodsVo goods=goodsService.getGoodsVoByGoodsId(goodsId);
        order.setGoods(goods);
        order.setOrder(orderInfo);
        return Result.success(order);
    }
}
