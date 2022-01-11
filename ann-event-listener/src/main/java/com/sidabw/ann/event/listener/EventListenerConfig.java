/**
 * Copyright (C), 2018-2021, zenki.ai
 * FileName: EventListenerConfig
 * Author:   feiyi
 * Date:     2021/3/23 4:09 PM
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.ann.event.listener;

import com.sidabw.ann.event.source.CustomEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * 〈一句话功能简述〉:
 * 〈〉
 *
 * @author feiyi
 * @create 2021/3/23
 * @since 1.0.0
 */
@Configuration
@Slf4j
public class EventListenerConfig {

    /**
     * 监听所有事件 可以看看 系统各类时间 发布了哪些事件
     * 可根据 instanceof 监听想要监听的事件
     *
     * 所以基本没有这么用的。。。
//     * @param event event
     */
//    @EventListeners
//    public void handleEvent(Object event) {
////        if(event instanceof CustomEvent) {
////
////        }
//        log.info("事件：{}", event);
//    }

    @EventListener
    public void handleCustomEvent(CustomEvent customEvent) {
        //监听 CustomEvent事件
        //默认是同步的，加@Async改异步
        log.info("监听到CustomEvent事件，消息为：{}, 发布时间：{}, 线程: {}", customEvent.getMessageEntity(), customEvent.getTimestamp(), Thread.currentThread().getId());
    }

    /**
     * 监听 code为oKong的事件
     */
    @EventListener(condition="#customEvent.messageEntity.code == 'oKong'")
    public void handleCustomEventByCondition(CustomEvent customEvent) {
        //监听 CustomEvent事件
        log.info("监听到code为'oKong'的CustomEvent事件，消息为：{}, 发布时间：{}", customEvent.getMessageEntity(), customEvent.getTimestamp());
    }
}
