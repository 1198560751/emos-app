package com.example.emos.wx.config.shiro;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.Date;

/**
 * shiro 与 JWT 工具类
 */
@Component
@Slf4j
public class JwtUtil {
    @Value("${emos.jwt.secret}")
    private String secret;

    @Value("${emos.jwt.expire}")
    private int expire;

    /**
     * 创建Token
     * @param userId 用户id
     */
    public String createToken(int userId) {
        //算出五天以后是什么时候.向后偏移五天
        Date date = DateUtil.offset(new Date(), DateField.DAY_OF_YEAR, 5);
        // 加密算法
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 创建JWT 对象
        JWTCreator.Builder builder = JWT.create();
        // 字段名称 过期时间  加密算法
       return builder.withClaim("userId", userId).withExpiresAt(date).sign(algorithm);
    }

    /**
     * 通过令牌获取UserId
     */
    public int getUserId(String token) {
        DecodedJWT jwt = JWT.decode(token);
        // 界面获取userId 转换为Int
        return jwt.getClaim("userId").asInt();
    }

    /**
     * 验证令牌可行性
     */
    public void verifierToken(String token) {
        // 创建加密对象
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 验证对象
        JWTVerifier build = JWT.require(algorithm).build();
        // 验证方法
        build.verify(token);
    }

}
