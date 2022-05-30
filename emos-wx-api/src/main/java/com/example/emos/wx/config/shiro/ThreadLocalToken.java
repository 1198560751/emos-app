package com.example.emos.wx.config.shiro;

import org.springframework.stereotype.Component;

/**
 * 创建存储令牌媒介类
 */
@Component
public class ThreadLocalToken {
    private ThreadLocal<String> local = new InheritableThreadLocal<>();

    /**
     * 保存token到ThreadLocal
     */
    public void setToken(String token) {
        local.set(token);
    }

    /**
     * 从ThreadLocal获取token令牌
     */
    public String getToken() {
        return local.get();
    }

    /**
     * 清空token令牌
     */
    public void clear() {
        local.remove();
    }
}
