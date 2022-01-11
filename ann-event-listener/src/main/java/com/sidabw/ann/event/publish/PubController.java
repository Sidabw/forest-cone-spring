/**
 * Copyright (C), 2018-2021, zenki.ai
 * FileName: PubController
 * Author:   feiyi
 * Date:     2021/3/23 4:11 PM
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.ann.event.publish;

import com.sidabw.ann.event.source.CustomEvent;
import com.sidabw.ann.event.source.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉:
 * 〈〉
 *
 * @author feiyi
 * @create 2021/3/23
 * @since 1.0.0
 */
@RestController
@RequestMapping("/pub")
@Slf4j
public class PubController {

    /**
     * 注入 事件发布类
     */
    @Autowired
    ApplicationEventPublisher eventPublisher;

    private CustomEvent customEvent;

    @GetMapping(value = "/pub1")
    public String pub1() {
        String code = "sidabw";
        String message = "sidabw-message";
        log.info("发布applicationEvent事件:{},{}, {}", code, message, Thread.currentThread().getId());
        if (this.customEvent == null) {
            this.customEvent = new CustomEvent(this, MessageEntity.builder().code(code).message(message).build());
        }
        eventPublisher.publishEvent(this.customEvent);
        return "事件发布成功!";
    }

    @GetMapping(value = "/pub2")
    public String pub2() {
        String code = "oKong";
        String message = "oKong-message";
        log.info("发布applicationEvent事件:{},{}", code, message);
        eventPublisher.publishEvent(new CustomEvent(this, MessageEntity.builder().code(code).message(message).build()));
        return "事件发布成功!";
    }

//    @GetMapping("/obj")
//    public String pushObject(String code,String message) {
//        log.info("发布对象事件:{},{}", code, message);
//        eventPublisher.publishEvent(MessageEntity.builder().code(code).message(message).build());
//        return "对象事件发布成功!";
//    }

}
