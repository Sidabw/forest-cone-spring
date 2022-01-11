/**
 * Copyright (C), 2018-2021, bokecc.com FileName: ProducerTest Author:   shaogz Date:     2021/5/24 11:38 AM
 * Description: abc History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.mq.rabbit.test1.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import com.sidabw.mq.rabbit.test.comsumer.direct.MqPerson;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 〈一句话功能简述〉:
 * 〈abc〉
 *
 * @author shaogz
 * @create 2021/5/24
 * @since 1.0.0
 */
public class ProducerTest {

    private static final String EXCHANGE_NAME = "first-spring-direct-exchange-2";

    private static final String ROUTING_KEY = "";

    private static final String QUEUE_NAME = "second-queue-2";

    private static final String IP_ADDRESS = "localhost";

    private static final int PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(IP_ADDRESS);
        connectionFactory.setPort(PORT);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();



        final int concurrent = 20;
        ExecutorService executorService = Executors.newFixedThreadPool(concurrent);
        CountDownLatch countDownLatch = new CountDownLatch(concurrent);
        int i = concurrent;
        AtomicInteger atomicInteger = new AtomicInteger();
        while (i >0) {
            executorService.execute(() ->{
                try {
                    countDownLatch.countDown();
                    countDownLatch.await();

                    Channel channel = connection.createChannel();
                    //创建一个 type=”direct”、持久化的、非自动删除的交换器
                    channel.exchangeDeclare(EXCHANGE_NAME, "direct", true,false,null);
                    ///创建一个持久化、非排他的、非自动删除的队列
                    Map<String,Object> arguments = new HashMap<>();
                    // 消息过期时长，10秒过期
                    arguments.put("x-message-ttl",10000);
                    channel.queueDeclare(QUEUE_NAME, true, false, false, arguments);
                    //将交换器与队列绑定
                    //注意：虽然这是一个RoutingKey，但实际应该是叫做BindingKey。
                    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
                    //注意：以上操作都不属于发送端。这些操创建交换机、创建队列、队列绑定的操作可以提前完成。
                    //发送消息只需要指定exchange、routingkey即可。
                    //对应的，接收消息只需要指定queue即可。（如果要使用临时队列，那肯定得指定exchange）
                    String message = new ObjectMapper().writeValueAsString(new MqPerson(1, "abc"));
                    AMQP.BasicProperties basicProperties = new AMQP.BasicProperties.Builder().contentType("application/json").timestamp(new Date()).build();
                    channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, basicProperties, message.getBytes());
                    System.out.println(atomicInteger.incrementAndGet());
                    //关闭资源
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            i--;
        }
        // if (atomicInteger.get() == concurrent) {
        //     executorService.shutdown();
        // }
        TimeUnit.SECONDS.sleep(1000);

        connection.close();
    }

}
