/**
 * Copyright (C), 2018-2021, zenki.ai
 * FileName: Producer
 * Author:   feiyi
 * Date:     2021/3/22 1:02 PM
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.mq.rabbit.test.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sidabw.mq.rabbit.test.RabbitConfig;
import com.sidabw.mq.rabbit.test.comsumer.direct.MqPerson;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 〈一句话功能简述〉:
 * 〈〉
 *
 * @author feiyi
 * @create 2021/3/22
 * @since 1.0.0
 */
@RestController
public class ProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send")
    public void sendMessage(){
        String message = "你好，我是Java旅途";
        rabbitTemplate.convertAndSend(RabbitConfig.exchangeName,null,message);
    }

    @GetMapping("/send2")
    public void sendMessage2() throws JsonProcessingException {
        MqPerson mqPerson = new MqPerson(1, "你好，我是Java旅途");
        String strMqPerson = new ObjectMapper().writeValueAsString(mqPerson);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setTimestamp(new Date());
        Message message = new Message(strMqPerson.getBytes(), messageProperties);
        rabbitTemplate.convertAndSend(RabbitConfig.exchangeName, RabbitConfig.routingKey, message);
    }

}
