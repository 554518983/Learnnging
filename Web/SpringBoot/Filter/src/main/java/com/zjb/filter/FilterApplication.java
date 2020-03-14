package com.zjb.filter;

import com.zjb.filter.filter.BeanFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

/**
 * @author zhaojianbo
 * @date 2020/3/14 22:39
 */
@SpringBootApplication
@ServletComponentScan
public class FilterApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilterApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean registrationBean() {
        FilterRegistrationBean filter = new FilterRegistrationBean(new BeanFilter());
        filter.addUrlPatterns("/*");
        filter.setOrder(2);
        return filter;
    }
}
