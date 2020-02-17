package com.miaosha.day1.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;



@Service
public class MQReciever {
    private static Logger log= LoggerFactory.getLogger(MQReciever.class);

    /**
     * direct模式  交换机模式Exchange
     * @param message
     */
    @RabbitListener(queues= MQConfig.QUEUE)
    public void receive(String message){
        log.info("receive message:"+message);
    }

    /**
     * topic模式
     * @param message
     */
    @RabbitListener(queues= MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message){
        log.info("receive topic queue1 message:"+message);
    }

    @RabbitListener(queues= MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message){
        log.info("receive topic queue2 message:"+message);
    }

    /**
     * headers模式
     * @param message
     */
    @RabbitListener(queues= MQConfig.HEADERS_QUEUE)
    public void receiveHeaders(byte[] message){
        log.info("receive headers queue2 message:"+new String(message));
    }

}
