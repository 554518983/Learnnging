package com.zjb.configurationfile.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 读取application.yaml中的内容
 * @author zhaojianbo
 * @date 2020/2/22 19:00
 */
@Data
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "zjb")
public class ConfigProperties {
    /**
     * 获取单个节点
     */
    @Value("${zjb.demo}")
    private String hello;

    /**
     * 与configurationProperites配合使用读取list
     */
    private List<String> numbers;
}
