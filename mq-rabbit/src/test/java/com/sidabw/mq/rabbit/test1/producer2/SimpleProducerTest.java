package com.sidabw.mq.rabbit.test1.producer2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * @author shaogz
 */
public class SimpleProducerTest {

    private static final String EXCHANGE_NAME = "hd-live-exchange-replay-api-static";

    private static final String ROUTING_KEY = "hd-live-routing-replay-api-static";

    private static final String IP_ADDRESS = "192.168.1.27";

    private static final int PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(IP_ADDRESS);
        connectionFactory.setPort(PORT);
        connectionFactory.setVirtualHost("hd-replay");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties.Builder().contentType("application/json").timestamp(new Date()).build();
        HashMap<String, String> msg = new HashMap<>();
        msg.put("requestId", "aaa");
        msg.put("actionName", "record-stop");
        HashMap<String, Object> data = new HashMap<>();
        data.put("recordId", 10027189);
        data.put("duration", 28);
        msg.put("data", new ObjectMapper().writeValueAsString(data));
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, basicProperties, new ObjectMapper().writeValueAsBytes(msg));
        channel.close();
        connection.close();
    }
}
