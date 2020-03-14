package com.zjb.filter.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * @author zhaojianbo
 * @date 2020/3/14 22:27
 */
@Order(1)
@WebFilter(filterName = "piceFilter", urlPatterns = "/*", initParams = {@WebInitParam(name = "URL", value = "http://localhost:8081")})
public class PiceaFilter implements Filter {
    private String url;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.url = filterConfig.getInitParameter("URL");
        System.out.println("start......," + this.url);
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("我是过滤器的执行方法，客户端向Servlet发送的请求被我拦截到了");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("我是过滤器的执行方法，Servlet向客户端发送的响应被我拦截到了");
    }

    public void destroy() {
        System.out.println("stop......");
    }
}
