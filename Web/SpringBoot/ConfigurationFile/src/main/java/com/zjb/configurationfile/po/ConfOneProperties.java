package com.zjb.configurationfile.po;

import com.zjb.configurationfile.config.YamlPropertyConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 * propertySource默认只能加载.properties结尾的文件
 * @author zhaojianbo
 * @date 2020/2/22 20:08
 */
@Data
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@PropertySource(value = {"classpath:application-one.yaml"}, factory = YamlPropertyConfig.class)
@ConfigurationProperties(prefix = "test")
public class ConfOneProperties {

    private List<String> wordlist;
}
