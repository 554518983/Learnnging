package com.zjb.filter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaojianbo
 * @date 2020/3/14 22:38
 */
@RestController
public class TestController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello wrold";
    }
}
