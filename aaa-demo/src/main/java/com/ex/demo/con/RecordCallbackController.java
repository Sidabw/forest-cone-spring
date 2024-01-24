package com.ex.demo.con;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * bokecc回调controller
 *
 * @author shaogz
 */
@RestController
public class RecordCallbackController {

    @RequestMapping(value = "/recordCallback4")
    public Map<String, String> recordCallback4(){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.singletonMap("s", "dd");
    }
    @RequestMapping(value = "/recordCallback")
    public Map<String, String> recordCallback(String userId, String roomId, String liveId, String recordId, String type,
        String startTime, String endTime, String recordStatus, String sourcetype, String recordVideoId,
        String recordVideoDuration, String replayUrl) {

        System.out.println("userId: " + userId);
        System.out.println("roomId: " + roomId);
        System.out.println("liveId: " + liveId);
        System.out.println("recordId: " + recordId);
        System.out.println("type: " + type);
        System.out.println("startTime: " + startTime);
        System.out.println("endTime: " + endTime);
        System.out.println("recordStatus: " + recordStatus);
        System.out.println("sourcetype: " + sourcetype);
        System.out.println("recordVideoId: " + recordVideoId);
        System.out.println("recordVideoDuration: " + recordVideoDuration);
        System.out.println("replayUrl: " + replayUrl);

        return Collections.singletonMap("result", "OK");
    }


    @RequestMapping(value = "/recordCallback2")
    public Map<String, String> recordCallback2(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((k, v) -> System.out.println(k + " : " + Arrays.toString(v)));
        return Collections.singletonMap("result", "OK");
    }

    @RequestMapping(value = "/recordCallback3")
    public Map<String, String> recordCallback3(@RequestParam Map<String, String> map) {
        //不加@RequestParam是拿不到query string 上的参数的
        map.forEach((k, v) -> System.out.println(k + " : " + v));
        return Collections.singletonMap("result", "OK");
    }


    @RequestMapping(value = "/chatCallback")
    public Map<String, Object> chatCallback(@RequestParam Map<String, String> map) {
        map.forEach((k, v) -> System.out.println(k + " : " + v));
        HashMap<String, Object> res = new HashMap<>();
        res.put("success", true);
        res.put("data", Collections.singletonMap("action", 1));
        return res;
    }

}
