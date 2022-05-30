package com.example.emos.wx.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * 令牌封装成认证对象
 */

public class OAuth2Token implements AuthenticationToken {
    private final String token;

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    public OAuth2Token(String token) {
        this.token = token;
    }
}
