package com.zjb.aop.controller;

import com.zjb.aop.service.AopTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaojianbo
 * @date 2020/2/23 12:11
 */
@RestController
public class AopTest2Controller {
    @Autowired
    private AopTestService aopTestService;

    @RequestMapping("/test")
    public String test() {
        return aopTestService.test();
    }

    @RequestMapping("/test02")
    public String test02() {
        return "controller" + aopTestService.test02();
    }
}
