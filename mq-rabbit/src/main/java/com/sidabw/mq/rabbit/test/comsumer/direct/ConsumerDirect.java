/**
 * Copyright (C), 2018-2021, zenki.ai FileName: ConsumerDirect Author:   feiyi Date:     2021/3/29 4:50 PM Description:
 * 直连交换机的消费者 History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.mq.rabbit.test.comsumer.direct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 〈一句话功能简述〉:
 * 〈直连交换机的消费者〉
 *
 * @author feiyi
 * @create 2021/3/29
 * @since 1.0.0
 */
@Slf4j
@Component
public class ConsumerDirect {

    private static AtomicInteger atomicInteger = new AtomicInteger();

    private static long start = -1;

    private static long end = -1;

    // @RabbitListener(queues = RabbitConfig.queueNameB)
    // public void receiveMetaMsg(MqPerson mqPerson, Channel channel, Message message) throws IOException, InterruptedException {
    //     log.debug("direct msg received:{}", mqPerson);
    //     TimeUnit.SECONDS.sleep(13);
    //     // log.debug("time:{}", message.getMessageProperties().getTimestamp().getTime());
    //     channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    //
    //     atomicInteger.incrementAndGet();
    //     if (atomicInteger.get() == 1) {
    //         start = System.currentTimeMillis();
    //     } else if (atomicInteger.get() == 4000) {
    //         end = System.currentTimeMillis();
    //         System.out.println("total time consumed: " + (end - start));
    //         atomicInteger.set(0);
    //     }
    //
    // }

}


