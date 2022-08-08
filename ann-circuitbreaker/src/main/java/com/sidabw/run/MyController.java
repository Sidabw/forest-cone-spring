package com.sidabw.run;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    private MyService myService;

    @GetMapping(value = "/getA")
    public String getA(){
        return myService.getA();
    }

    @Autowired
    public void setMyService(MyService myService) {
        this.myService = myService;
    }
}
