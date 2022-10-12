/**
 * Copyright (C), 2018-2021, bokecc.com FileName: GreetingHandler Author:   shaogz Date:     2021/6/3 2:01 PM
 * Description: History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.webflux;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * 〈一句话功能简述〉:
 * 〈〉
 *
 * @author shaogz
 * @create 2021/6/3
 * @since 1.0.0
 */
@Component
public class GreetingHandler {

    public Mono<ServerResponse> hello(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(BodyInserters.fromValue("Hello world"));
    }
}
