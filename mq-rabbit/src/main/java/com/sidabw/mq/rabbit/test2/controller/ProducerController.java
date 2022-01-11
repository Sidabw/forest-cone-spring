/**
 * Copyright (C), 2018-2021, bokecc.com FileName: ProducerController Author:   shaogz Date:     2021/5/7 10:27 AM
 * Description: History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.mq.rabbit.test2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sidabw.mq.rabbit.test2.conf.RabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * 〈一句话功能简述〉:
 * 〈〉
 *
 * @author shaogz
 * @create 2021/5/7
 * @since 1.0.0
 */
@RestController
public class ProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send")
    public void sendMessage(){
        String message = "你好，我是Java旅途";
        rabbitTemplate.convertAndSend(RabbitConfig.exchangeName, null, message);
    }

    @GetMapping("/send2")
    public void sendMessage2() throws JsonProcessingException {
        if(System.currentTimeMillis() % 2 == 0) {
            System.out.println(2 + " : " + System.currentTimeMillis());
        }
        Map<String, String> mqPerson = Collections.singletonMap("name", "你好，我是Java旅途");
        String strMqPerson = new ObjectMapper().writeValueAsString(mqPerson);

        // MessageProperties messageProperties = new MessageProperties();
        // messageProperties.setTimestamp(new Date());
        // Message message = new Message(strMqPerson.getBytes(), messageProperties);

        Message message = MessageBuilder.withBody(strMqPerson.getBytes())
            .setContentType(MessageProperties.CONTENT_TYPE_JSON)
            .setContentEncoding("utf-8")
            .setMessageId(UUID.randomUUID()+"")
            .setTimestamp(new Date())
            .build();
        rabbitTemplate.convertAndSend(RabbitConfig.exchangeName, RabbitConfig.routingKey, message);
    }

}
