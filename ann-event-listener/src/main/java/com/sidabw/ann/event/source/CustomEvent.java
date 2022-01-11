/**
 * Copyright (C), 2018-2021, zenki.ai
 * FileName: CustomEvent
 * Author:   feiyi
 * Date:     2021/3/23 4:08 PM
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.ann.event.source;

import org.springframework.context.ApplicationEvent;

/**
 * 〈一句话功能简述〉:
 * 〈〉
 *
 * @author feiyi
 * @create 2021/3/23
 * @since 1.0.0
 */
public class CustomEvent extends ApplicationEvent {

    private MessageEntity messageEntity;

    public CustomEvent(Object source, MessageEntity messageEntity) {
        super(source);
        this.messageEntity = messageEntity;
    }

    public MessageEntity getMessageEntity() {
        return this.messageEntity;
    }
}
