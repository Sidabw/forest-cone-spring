package com.sidabw.ann.cache.basic2.run;///**

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉:
 * 〈〉
 *
 * @author feiyi
 * @create 2021/3/22
 * @since 1.0.0
 */
@RestController
public class ModelController {

    @Autowired
    private ModelService modelService;

    @GetMapping(value = "/getModel1")
    public ModelEntity getModel1() throws InterruptedException {
        return modelService.getCache1("aa");
    }

    @GetMapping(value = "/getModel2")
    public ModelEntity getModel2() throws InterruptedException {
        return modelService.getCache2("aa");
    }

    @GetMapping(value = "/getModel3")
    public ModelEntity getModel3() throws InterruptedException {
        return modelService.getCache3("aa");
    }
}
