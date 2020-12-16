package com.sidabw.forest.cone.mybatisplus.generator.controller;


import com.sidabw.forest.cone.mybatisplus.generator.service.IModelChangeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author feiyi
 * @since 2020-12-16
 */
@RestController
@RequestMapping("/model-change-log")
public class ModelChangeLogController {

    @Autowired
    private IModelChangeLogService modelChangeLogService;

    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public Object getList(){
        return modelChangeLogService.list();
    }

    @RequestMapping(value = "/selfDesignSql1", method = RequestMethod.GET)
    public Object selfDesignSql1(){
        return modelChangeLogService.selfDesignSql1();
    }

    @RequestMapping(value = "/selfDesignSql2", method = RequestMethod.GET)
    public Object selfDesignSql2() {
        return modelChangeLogService.selfDesignSql2();
    }
}
