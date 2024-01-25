package com.sidabw.webhttpclient.resttemplate;

import com.sidabw.webhttpclient.resttemplate.pojo.SidaParams;
import com.sidabw.webhttpclient.resttemplate.pojo.SidaResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

/**
 * GET/POST/PUT/DELETE/OPTION/HEAD
 * <p>1. 参考1：https://www.baeldung.com/rest-template <br>
 * <p>2. 参考2：https://www.baeldung.com/httpclient-timeout
 * @author shaogz
 */
@Slf4j
public class Demo1 {

    @Test
    public void testGet() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = "https://8848-103-90-191-98.ngrok.io/paramsTest4?a=222";
        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        String bodyStr = response.getBody();
        System.out.println(bodyStr);

        ResponseEntity<SidaResult> response2 = restTemplate.getForEntity(fooResourceUrl, SidaResult.class);
        SidaResult body = response2.getBody();
        assert body != null;
        System.out.println(body.getSuccess());
    }


    @Test
    public void testPost(){
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<SidaParams> request = new HttpEntity<>(new SidaParams("bar", "bar", "bar"));
        String fooResourceUrl = "https://8848-103-90-191-98.ngrok.io/paramsTest4";
        SidaResult foo = restTemplate.postForObject(fooResourceUrl, request, SidaResult.class);
        System.out.println(foo);

        //Create a new resource by POSTing the given object to the URI template, and returns the value of the Location header. This header typically indicates where the new resource is stored.
        //所以感觉这个...  现在是用不到
        //restTemplate.postForLocation()
    }

    @Test
    public void testFormPost(){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("id", "1");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        String fooResourceUrl = "https://8848-103-90-191-98.ngrok.io/paramsTest4";
        ResponseEntity<SidaResult> response = restTemplate.postForEntity(fooResourceUrl, request , SidaResult.class);
        System.out.println(response.getBody().getSuccess());

    }

    /*

     ***ATTENTION***
     如下put和delete，都是没有返回值的。
     所以在设计RESTFul Web API时，put与delete应当都是void。
     资源修改/删除是否成功，通过http code判断
     ***ATTENTION***

     */

    @Test
    public void testPut() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = "https://8848-103-90-191-98.ngrok.io/paramsTest4?a=put";
        //application-json
        HttpEntity<SidaParams> request = new HttpEntity<>(new SidaParams("bar", "bar", "bar"));
        //void
        restTemplate.put(fooResourceUrl, request);
        //这样也可以
        restTemplate.exchange(fooResourceUrl, HttpMethod.PUT, request, Void.class);
    }

    @Test
    public void testDelete() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = "https://8848-103-90-191-98.ngrok.io/paramsTest4?a=del";
        //void
        restTemplate.delete(fooResourceUrl);
    }


    /**
     *  询问服务器该URI的支持的请求方法 Use OPTIONS to Get Allowed Operations
     * @author shaogz
     * @since  2022/2/10 3:23 PM
     **/
    @Test
    public void testOptions() {
        String fooResourceUrl = "https://8848-103-90-191-98.ngrok.io/paramsTest";
        RestTemplate restTemplate = new RestTemplate();
        Set<HttpMethod> optionsForAllow = restTemplate.optionsForAllow(fooResourceUrl);
        optionsForAllow.forEach(System.out::println);
        //拿不到，don't know why
    }


    /**
     * head请求，只拿返回头信息。通常用来用于验证uri有效性或拿到资源修改时间。
     * @author shaogz
     * @since  2022/2/10 3:12 PM
     **/
    @Test
    public void testHead() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = "https://8848-103-90-191-98.ngrok.io/paramsTest4?a=222";
        HttpHeaders httpHeaders = restTemplate.headForHeaders(fooResourceUrl);
        Set<HttpMethod> allow = httpHeaders.getAllow();
        allow.forEach(e -> System.out.println(e.name()));
        httpHeaders.getAccept().forEach(System.out::println);
        httpHeaders.entrySet().forEach(System.out::println);
    }

}
