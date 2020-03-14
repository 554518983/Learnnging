package com.zjb.filter.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhaojianbo
 * @date 2020/3/14 22:48
 */
public class BeanFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("bean start......");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("beanFilter 被拦截到了");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
        System.out.println("bean stop.....");
    }
}
