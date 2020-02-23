package com.zjb.aop.service.impl;

import com.zjb.aop.anon.WebLog;
import com.zjb.aop.service.AopTestService;
import org.springframework.stereotype.Service;

/**
 * @author zhaojianbo
 * @date 2020/2/23 12:05
 */
@Service
public class AopTestServiceImpl implements AopTestService {

    @WebLog()
    public String test() {
        return "test";
    }

    @WebLog()
    public String test02() {
        return "test000000";
    }
}
