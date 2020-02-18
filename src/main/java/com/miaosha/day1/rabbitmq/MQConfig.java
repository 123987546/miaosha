package com.miaosha.day1.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class MQConfig {
    public static final String QUEUE="queue";
    public static final String TOPIC_QUEUE1="topic.queue1";
    public static final String TOPIC_QUEUE2="topic.queue2";
    public static final String HEADERS_QUEUE="headers.queue2";
    public static final String MIAOSHA_QUEUE="miaosha.queue";
    public static final String TOPIC_EXCHANGE="topicExchange";
    public static final String FANOUT_EXCHANGE="fanoutExchange";
    public static final String HEADERS_EXCHANGE="headersExchange";
    public static final String ROUTING_KEY1="topic.key1";//routingkey绑定exchange将消息推给哪个消息队列
    public static final String ROUTING_KEY2="topic.#";
    /**
     * 注册消息队列
     * @return
     */
    @Bean
    public Queue queue(){
        return new Queue(QUEUE,true);
    }
    @Bean
    public Queue miaoshaqueue(){
        return new Queue(MIAOSHA_QUEUE,true);
    }

    /**
     * topic exchange
     * @return
     */
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }
    @Bean
    public Queue topicQueue1(){
        return new Queue(TOPIC_QUEUE1,true);
    }
    @Bean
    public Queue topicQueue2(){
        return new Queue(TOPIC_QUEUE2,true);
    }

    @Bean
    public Binding topicBinding1(){
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with(ROUTING_KEY1);
    }
    @Bean
    public Binding topicBinding2(){
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(ROUTING_KEY2);
    }
    /**
     * fanout exchange 广播
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding fanoutBinding1(){
        return BindingBuilder.bind(topicQueue1()).to(fanoutExchange());
    }
    @Bean
    public Binding fanoutBinding2(){
        return BindingBuilder.bind(topicQueue2()).to(fanoutExchange());
    }

    /**
     * head exchange
     * @return
     */
    @Bean
    public HeadersExchange headersExchange(){
        return new HeadersExchange(HEADERS_EXCHANGE);
    }
    @Bean
    public Queue headersQueue1(){
        return new Queue(HEADERS_QUEUE,true);
    }
    @Bean
    public Binding headersBinding1(){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("headers1","value1");
        map.put("headers2","value2");
        return BindingBuilder.bind(headersQueue1()).to(headersExchange()).whereAll(map).match();//必满足有这个key和value才会向这里面放东西
    }
}
