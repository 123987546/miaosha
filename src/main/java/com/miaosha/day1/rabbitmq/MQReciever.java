package com.miaosha.day1.rabbitmq;

import com.miaosha.day1.domain.MiaoshaOrder;
import com.miaosha.day1.domain.MiaoshaUser;
import com.miaosha.day1.service.GoodsService;
import com.miaosha.day1.service.MiaoshaService;
import com.miaosha.day1.service.OrderService;
import com.miaosha.day1.redis.RedisService;
import com.miaosha.day1.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class MQReciever {
    private static Logger log= LoggerFactory.getLogger(MQReciever.class);

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;
    @RabbitListener(queues= MQConfig.MIAOSHA_QUEUE)
    public void receive(String message){
        log.info("receive message:"+message);
        MiaoshaMessage mm=RedisService.stringToBean(message,MiaoshaMessage.class);
        MiaoshaUser user=mm.getUser();
        long goodsId=mm.getGoodsId();
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);//10个商品，req1 req2
        int stock = goods.getStockCount();
        if(stock <= 0) {
            return;
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return;
        }
        //减库存 下订单 写入秒杀订单
        miaoshaService.miaosha(user, goods);
    }
//    /**
//     * direct模式  交换机模式Exchange
//     * @param message
//     */
//    @RabbitListener(queues= MQConfig.QUEUE)
//    public void receive(String message){
//        log.info("receive message:"+message);
//    }
//
//    /**
//     * topic模式
//     * @param message
//     */
//    @RabbitListener(queues= MQConfig.TOPIC_QUEUE1)
//    public void receiveTopic1(String message){
//        log.info("receive topic queue1 message:"+message);
//    }
//
//    @RabbitListener(queues= MQConfig.TOPIC_QUEUE2)
//    public void receiveTopic2(String message){
//        log.info("receive topic queue2 message:"+message);
//    }
//
//    /**
//     * headers模式
//     * @param message
//     */
//    @RabbitListener(queues= MQConfig.HEADERS_QUEUE)
//    public void receiveHeaders(byte[] message){
//        log.info("receive headers queue2 message:"+new String(message));
//    }

}
