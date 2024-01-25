package com.sidabw.webhttpclient.resttemplate;

import com.sidabw.webhttpclient.resttemplate.pojo.SidaParams;
import com.sidabw.webhttpclient.resttemplate.pojo.SidaResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 *
 * 超时时间的相关问题
 * @author shaogz
 * @since 2024/1/25 09:29
 */
@Slf4j
public class Demo2 {

    @Test
    public void testExternalConfig() {
        //方式一
        int timeout = 5000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        //1. 从连接池中获取链接的超时时间（the time to wait for a connection from the connection manager/pool）
        clientHttpRequestFactory.setConnectionRequestTimeout(timeout);
        //2. 与远程主机建立链接的超时时间（the time to establish the connection with the remote host）
        clientHttpRequestFactory.setConnectTimeout(timeout);
        //3. 建立链接后给到两个响应包的最长间隔时间（ – the time waiting for data – after establishing the connection; maximum time of inactivity between two data packets）
        clientHttpRequestFactory.setReadTimeout(timeout);
        new RestTemplate(clientHttpRequestFactory);


        //方式二
        int timeout2 = 1000;
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(timeout2)
                .setSocketTimeout(timeout2)
                .setConnectTimeout(timeout2)
                .build();
        CloseableHttpClient client = HttpClientBuilder
                .create()
                .setDefaultRequestConfig(config)
                .build();
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory2 = new HttpComponentsClientHttpRequestFactory(client);
        RestTemplate restTemplate2 = new RestTemplate(clientHttpRequestFactory2);
        /*

         ***ATTENTION***
         1。真正的超时时间是上面设置的三个时间的和
         2。这三个加起来就是最长请求结束的时间吗？不是的，超大文件的下载，响应包一直write，客户端这边要读好久，那...  就是好久..
         3。默认都是配置了连接池的；这个连接池说的是TCP物理链接，即http的keep alive属性；可能里面的maxConnections or maxConnectionsPerRoute不满足

         ***ATTENTION***

        */
        HttpEntity<SidaParams> request = new HttpEntity<>(new SidaParams("bar", "bar", "bar"));
        String fooResourceUrl = "https://a085-103-90-191-98.ngrok.io/paramsTest4";
        SidaResult foo = restTemplate2.postForObject(fooResourceUrl, request, SidaResult.class);
        System.out.println(foo);

    }

}
