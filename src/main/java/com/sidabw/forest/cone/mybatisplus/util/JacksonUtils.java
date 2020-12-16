/**
 * Copyright (C), 2018-2020, zenki.ai
 * FileName: JacksonUtils
 * Author:   feiyi
 * Date:     2020/12/16 10:59 AM
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.forest.cone.mybatisplus.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;

/**
 * 〈一句话功能简述〉:
 * 〈〉
 *
 * @author feiyi
 * @create 2020/12/16
 * @since 1.0.0
 */
public class JacksonUtils {

    public static String convertToJsonStr(Object scanContentBo) {
        ObjectMapper mapper = new ObjectMapper();
        String messageContent;
        try {
            messageContent = mapper.writeValueAsString(scanContentBo);
        } catch (Exception e) {
            System.out.println("failed!");
            return null;
        }
        return messageContent;
    }

    public static  <T> T  parseStrToJson(String originStr, Class<T> tClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(originStr, tClass);
    }
}