package com.example.emos.wx.controller;

import com.example.emos.wx.common.util.R;
import com.example.emos.wx.config.shiro.JwtUtil;
import com.example.emos.wx.controller.form.LoginForm;
import com.example.emos.wx.controller.form.RegisterForm;
import com.example.emos.wx.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Set;

/**
 * 用户控制层
 */
@RestController
@RequestMapping("/user")
@Api("用户模块web接口")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Value("${emos.jwt.cache-expire}")     //过期时间
    private int cacheExpire;

    @PostMapping("/register")
    @ApiOperation("用户注册接口")
    public R register(@Valid @RequestBody RegisterForm form) {
        int userId = userService.registerUser(form.getRegisterCode(), form.getCode(), form.getNickname(), form.getPtoto());
        String token = jwtUtil.createToken(userId);
        Set<String> permsSet = userService.searchUserPermissions(userId);
        //调用缓存
        saveCacheToken(token, userId);
        return Objects.requireNonNull(R.ok("用户注册成功").put("token", token)).put("permission", permsSet);
    }

    @PostMapping("/login")
    @ApiOperation("用户登录接口")
    public R login(@Valid @RequestBody LoginForm form) {
        Integer id = userService.login(form.getCode());
        //生成token字符串
        String token = jwtUtil.createToken(id);
        //保存到redis
        saveCacheToken(token, id);
        Set<String> permsSet = userService.searchUserPermissions(id);
        return Objects.requireNonNull(R.ok("登录成功").put("token", token)).put("permission", permsSet);
    }




    /**
     * 保存令牌到redis
     */
    private void saveCacheToken(String token, int userId) {
        redisTemplate.opsForValue().set(token, userId + "", cacheExpire);
    }
}
