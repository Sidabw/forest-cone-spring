/**
 * Copyright (C), 2018-2021, bokecc.com FileName: RabbitConfig Author:   shaogz Date:     2021/5/7 10:25 AM Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.mq.rabbit.test2.conf;

import org.springframework.context.annotation.Configuration;

/**
 * 〈一句话功能简述〉:
 * 〈〉
 *
 * @author shaogz
 * @create 2021/5/7
 * @since 1.0.0
 */
@Configuration
public class RabbitConfig {

    public final static String queueNameA = "first-queue-2";
    public final static String queueNameB = "second-queue";

    public final static String exchangeName = "first-spring-direct-exchange-2";

    // public static final String routingKey = "info";
    public static final String routingKey = "";

    //
    // @Bean
    // public ObjectMapper serializingObjectMapper() {
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    //     objectMapper.registerModule(new JavaTimeModule());
    //     return objectMapper;
    // }
    //
    // @Bean
    // public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
    //     return new Jackson2JsonMessageConverter(objectMapper);
    // }
    //
    // /***
    //  * 定义队列A，设置队列属性
    //  * @return Queue
    //  */
    // @Bean
    // public Queue queueA(){
    //
    //     Map<String,Object> arguments = new HashMap<>();
    //     // 消息过期时长，10秒过期
    //     arguments.put("x-message-ttl",10000);
    //     // 队列中最大消息条数，10条
    //     // arguments.put("x-max-length",10);
    //     // 四个参数: 队列名称, durable(持久化),exclusive(排外的), autoDelete：自动删除
    //     return QueueBuilder.durable(queueNameB).withArguments(arguments).build();
    // }
    //
    // @Bean
    // public Exchange exchange() {
    //     return ExchangeBuilder.directExchange(exchangeName).durable(true).build();
    // }
    //
    // @Bean
    // public Binding binding() {
    //     return BindingBuilder.bind(queueA()).to(exchange()).with(routingKey).noargs();
    // }

}
