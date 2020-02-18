package com.miaosha.day1.service;

import com.miaosha.day1.domain.MiaoshaOrder;
import com.miaosha.day1.domain.MiaoshaUser;
import com.miaosha.day1.domain.OrderInfo;
import com.miaosha.day1.redis.MiaoshaKey;
import com.miaosha.day1.redis.RedisService;
import com.miaosha.day1.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;
    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        //减库存 下订单 写入秒杀订单
        boolean success=goodsService.reduceStock(goods);
        if(success) {
            //order_info maiosha_order
            return orderService.createOrder(user, goods);
        }else{
            setGoodsOver(goods.getId());
            return null;
        }
    }




    public long getMiaoshaResult(Long userid, long goodsId) {
        MiaoshaOrder order=orderService.getMiaoshaOrderByUserIdGoodsId(userid,goodsId);
        if(order!=null){
            return order.getOrderId();
        }else{
            boolean isOver=getGoodsOver(goodsId);
            if(isOver){
                return -1;
            }else{
                return 0;
            }
        }
    }
    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.isGoodsOver,""+goodsId,true);
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(MiaoshaKey.isGoodsOver,""+goodsId);
    }

    public void reset(List<GoodsVo> goodsList) {
        goodsService.resetStock(goodsList);
        orderService.deleteOrders();
    }
}
