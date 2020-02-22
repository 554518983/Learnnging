package com.zjb.configurationfile.controller;

import com.zjb.configurationfile.po.ConfOneProperties;
import com.zjb.configurationfile.po.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhaojianbo
 * @date 2020/2/22 13:59
 */
@RestController
public class ConfigurationController {

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private ConfOneProperties confOneProperties;

    @RequestMapping("/hello")
    public String hello() {
        return configProperties.getHello();
    }

    @RequestMapping("/list")
    public List<String> list() {
        return configProperties.getNumbers();
    }

    @RequestMapping("/wordlist")
    public List<String> wordList() {
        List<String> words = confOneProperties.getWordlist();
        return words;
    }
}
