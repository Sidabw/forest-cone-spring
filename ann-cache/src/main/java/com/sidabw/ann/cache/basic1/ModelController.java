///**
// * Copyright (C), 2018-2021, zenki.ai
// * FileName: ModelController
// * Author:   feiyi
// * Date:     2021/3/22 4:19 PM
// * Description:
// * History:
// * <author>          <time>          <version>          <desc>
// * 作者姓名           修改时间           版本号              描述
// */
//package com.sidabw.ann.cache.basic1;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 〈一句话功能简述〉:
// * 〈〉
// *
// * @author feiyi
// * @create 2021/3/22
// * @since 1.0.0
// */
//@RestController
//public class ModelController {
//
//    @Autowired
//    private ModelService modelService;
//
//    @GetMapping(value = "/getModel")
//    public ModelEntity getModel() throws InterruptedException {
//        return modelService.getById("aa");
//    }
//
//    @GetMapping(value = "/getModel2")
//    public ModelEntity getModel2() throws InterruptedException {
//        return modelService.updateById("aa");
//    }
//
//    @GetMapping(value = "/getModel3")
//    public ModelEntity getModel3() throws InterruptedException {
//        return modelService.delById("aa");
//    }
//
////    @GetMapping(value = "/getModel4")
////    public ModelEntity getModel4() throws InterruptedException {
////        return modelService.springCacheWithRedisTTLSupport("aa");
////    }
//
//}
