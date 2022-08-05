package com.sidabw.ann.cache.basic4.run;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BypassController {

    private BypassService bypassService;

    @GetMapping(value = "/getBypassEntity")
    public BypassEntity getBypassEntity() {
        log.info("I'm here");
        return bypassService.getEntity("aaaaaaaaaaaaaaaa");
    }

    @Autowired
    public void setBypassService(BypassService bypassService) {
        this.bypassService = bypassService;
    }
}
