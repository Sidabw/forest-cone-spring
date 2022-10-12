/**
 * Copyright (C), 2018-2021, bokecc.com FileName: GreetingWebClient Author:   shaogz Date:     2021/6/3 2:14 PM
 * Description: History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.webflux;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * 〈一句话功能简述〉:
 * 〈〉
 *
 * @author shaogz
 * @create 2021/6/3
 * @since 1.0.0
 */
public class GreetingWebClient {

    /**
     * 这只是个手动测试...
     * 也可以直接请求  localhost:8080/hello
     */
    private WebClient client = WebClient.create("http://localhost:8080");

    private Mono<ClientResponse> result = client.get().uri("/hello").accept(MediaType.TEXT_PLAIN).exchange();

    public String getResult() {
        return ">> Result = " + result.flatMap(res -> res.bodyToMono(String.class)).block();
    }


}
