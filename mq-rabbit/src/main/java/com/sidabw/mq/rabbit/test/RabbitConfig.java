/**
 * Copyright (C), 2018-2021, zenki.ai
 * FileName: RabbitConfig
 * Author:   feiyi
 * Date:     2021/3/22 12:51 PM
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.mq.rabbit.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉:
 * 〈〉
 *
 * @author feiyi
 * @create 2021/3/22
 * @since 1.0.0
 */
@Configuration
@Slf4j
public class RabbitConfig {

    public final static String exchangeName = "first-spring-direct-exchange-2";

    public final static String queueNameA = "first-queue-2";
    public final static String queueNameB = "second-queue-2";


    public static final String routingKey = "info";
    // public static final String routingKey = "";


    @Bean
    public ObjectMapper serializingObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    /***
     * 定义队列A，设置队列属性
     * @return Queue
     */
    @Bean
    public Queue queueA(){

        Map<String,Object> arguments = new HashMap<>();
        // 消息过期时长，10秒过期
        arguments.put("x-message-ttl",10000);
        // 队列中最大消息条数，10条
        // arguments.put("x-max-length",10);
        // 四个参数: 队列名称, durable(持久化),exclusive(排外的), autoDelete：自动删除
        return QueueBuilder.durable(queueNameB).withArguments(arguments).build();
    }

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.directExchange(exchangeName).durable(true).build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queueA()).to(exchange()).with(routingKey).noargs();
    }

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private String port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        com.rabbitmq.client.ConnectionFactory rabbitConnectionFactory = new com.rabbitmq.client.ConnectionFactory();
        rabbitConnectionFactory.setHost(host);
        rabbitConnectionFactory.setPort(Integer.parseInt(port));
        rabbitConnectionFactory.setUsername(username);
        rabbitConnectionFactory.setPassword(password);
        rabbitConnectionFactory.setAutomaticRecoveryEnabled(true);
        rabbitConnectionFactory.setNetworkRecoveryInterval(5000);

        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(rabbitConnectionFactory);
        cachingConnectionFactory.setPublisherReturns(true);
        cachingConnectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);
        cachingConnectionFactory.setConnectionCacheSize(15);
        cachingConnectionFactory.setChannelCacheSize(200);
        cachingConnectionFactory.setChannelCheckoutTimeout(200);
        cachingConnectionFactory.setConnectionLimit(30);

        return cachingConnectionFactory;
    }


    // @Bean
    // public RabbitAdmin rabbitAdmin(ConnectionFactory rabbitConnectionFactory){
    //     return new RabbitAdmin(rabbitConnectionFactory);
    // }
    //
    // @Bean
    // public SimpleMessageListenerContainer messageListenerContainer() {
    //
    //     SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    //     container.setConnectionFactory(connectionFactory());
    //     container.setQueueNames(queueNameB);
    //     container.setConcurrentConsumers(10);
    //     container.setMessageListener(exampleListener());
    //     return container;
    // }


    // @Bean
    // public MessageListener exampleListener() {
    //     return new MessageListener() {
    //         @SneakyThrows @Override
    //         public void onMessage(Message message) {
    //             System.out.println("received: " + message);
    //             TimeUnit.SECONDS.sleep(1);
    //         }
    //     };
    // }
}
