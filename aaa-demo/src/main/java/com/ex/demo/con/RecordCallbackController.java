package com.ex.demo.con;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * bokecc回调controller
 *
 * @author shaogz
 */
@RestController
@Slf4j
public class RecordCallbackController {

    @RequestMapping(value = "/commonCallback1")
    public Map<String, String> recordCallback4() throws InterruptedException {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            log.error("fail", e);
            throw e;
        }
        return Collections.singletonMap("s", "dd");
    }

    @RequestMapping(value = "/commonCallback11")
    public void recordCallback41(HttpServletResponse response) throws InterruptedException, IOException {
        PrintWriter writer = response.getWriter();
        int i = 10_000;
        for (;i-->0;) {
            writer.write("1");
            TimeUnit.MILLISECONDS.sleep(100);
            log.info("write finish " + i);
        }
        writer.close();
    }

    @RequestMapping(value = "/commonCallback2")
    public Map<String, String> recordCallback2(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((k, v) -> log.info(k + " : " + Arrays.toString(v)));
        return Collections.singletonMap("result", "OK");
    }

    @RequestMapping(value = "/commonCallback3")
    public Map<String, String> recordCallback3(@RequestParam Map<String, String> map) {
        //不加@RequestParam是拿不到query string 上的参数的
        map.forEach((k, v) -> log.info(k + " : " + v));
        return Collections.singletonMap("result", "OK");
    }


    @RequestMapping(value = "/chatCallback")
    public Map<String, Object> chatCallback(@RequestParam Map<String, String> map) {
        map.forEach((k, v) -> log.info(k + " : " + v));
        HashMap<String, Object> res = new HashMap<>();
        res.put("success", true);
        res.put("data", Collections.singletonMap("action", 1));
        return res;
    }


    @RequestMapping(value = "/recordCallback")
    public Map<String, String> recordCallback(String userId, String roomId, String liveId, String recordId, String type,
                                              String startTime, String endTime, String recordStatus, String sourcetype, String recordVideoId,
                                              String recordVideoDuration, String replayUrl) {

        log.info("userId: " + userId);
        log.info("roomId: " + roomId);
        log.info("liveId: " + liveId);
        log.info("recordId: " + recordId);
        log.info("type: " + type);
        log.info("startTime: " + startTime);
        log.info("endTime: " + endTime);
        log.info("recordStatus: " + recordStatus);
        log.info("sourcetype: " + sourcetype);
        log.info("recordVideoId: " + recordVideoId);
        log.info("recordVideoDuration: " + recordVideoDuration);
        log.info("replayUrl: " + replayUrl);

        return Collections.singletonMap("result", "OK");
    }

}
