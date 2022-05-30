package com.example.emos.wx.config.xss;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 通过过滤器拦截所有请求,过滤掉Xss攻击
 */
@WebFilter(urlPatterns = "/*")
public class XssFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    /**
     * 重写doFilter方法
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //将servletRequest转换为HttpServletRequest
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //创建自己写的XssHttpServletRequestWrapper
        XssHttpServletRequestWrapper wrapper = new XssHttpServletRequestWrapper(request);
        //让程序过滤完成继续执行
        filterChain.doFilter(wrapper, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
