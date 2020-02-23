package com.zjb.aop.controller;

import com.zjb.aop.anon.WebLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaojianbo
 * @date 2020/2/23 10:57
 */
@RestController
public class AopTestController {

    @GetMapping("/hello")
    public String hello() {
        return "hi";
    }

    @GetMapping("/demo1")
    public String ex() {
        int num = 1 / 0;
        return "hello";
    }

    @WebLog()
    @GetMapping("/demo2")
    public String demo2() {
        return "demo2";
    }

}
