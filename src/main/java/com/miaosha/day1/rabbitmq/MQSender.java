package com.miaosha.day1.rabbitmq;

import com.miaosha.day1.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {
    @Autowired
    AmqpTemplate amqpTemplate; //系统会帮我们自动注入这个类

    private static Logger log= LoggerFactory.getLogger(MQReciever.class);

    public void send(Object message){
        String msg= RedisService.beanToString(message);
        log.info("send message:"+message);
        amqpTemplate.convertAndSend(MQConfig.QUEUE,msg);

    }

    public void sendTopic(Object message){
        String msg= RedisService.beanToString(message);
        log.info("send message:"+message);
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,MQConfig.ROUTING_KEY1,msg+"1");
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,MQConfig.ROUTING_KEY2,msg+"2");

    }

    public void sendFanout(Object message){
        String msg= RedisService.beanToString(message);
        log.info("send message:"+message);
        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE,"",msg+"1");
    }

    public void sendHeader(Object message){
        String msg= RedisService.beanToString(message);
        log.info("send message:"+message);
        MessageProperties properties=new MessageProperties();
        properties.setHeader("headers1","value1");
        properties.setHeader("headers2","value2");
        Message obj=new Message(msg.getBytes(),properties);
        amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE,"",obj);
    }


}
