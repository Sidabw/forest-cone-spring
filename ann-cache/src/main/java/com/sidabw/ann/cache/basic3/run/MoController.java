package com.sidabw.ann.cache.basic3.run;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MoController {

    private MoService1 moService1;


    @GetMapping(value = "/getEntity")
    public MoEntity getEntity() {
        log.info("here start");
        MoEntity moEntity = moService1.getEntity("aasdfa");
        log.info("here end");
        return moEntity;
    }

    @Autowired
    public void setMoService1(MoService1 moService1) {
        this.moService1 = moService1;
    }
}
