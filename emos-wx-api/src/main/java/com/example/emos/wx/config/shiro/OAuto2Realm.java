package com.example.emos.wx.config.shiro;

import com.example.emos.wx.db.pojo.TbUser;
import com.example.emos.wx.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 定义授权与认证方法
 */
@Component
public class OAuto2Realm extends AuthorizingRealm {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        //判断是不是自己定义的token对象类型
        return token instanceof OAuth2Token;
    }


    /**
     * 认证方法(验证登陆时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取字符串认证令牌
        String token = (String) authenticationToken.getPrincipal();
        //解析获取用户Id
        int userId = jwtUtil.getUserId(token);
        //查询用户信息
        TbUser user = userService.searchById(userId);
        if (user == null) {   //如果用户为空可能离职,则不给权限
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        //构建认证对象
        return new SimpleAuthenticationInfo(user, token, getName());
    }

    /**
     * 授权方法(认证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取用户基本信息
        TbUser user = (TbUser) principalCollection.getPrimaryPrincipal();
        // 查询用户的权限列表
        Set<String> permissions = userService.searchUserPermissions(user.getId());
        //构建授权对象
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 把权限列表添加到info对象中
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }
}
