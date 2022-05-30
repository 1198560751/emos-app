package com.example.emos.wx.config.shiro;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 过滤器  过滤请求
 */
@Component
//改成多例 因为是多线程并发
@Scope("prototype")
public class OAuto2Filter extends AuthenticatingFilter {
    @Autowired
    private ThreadLocalToken localToken;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 缓存过期时间
     */
    @Value("${emos.jwt.cache-expire}")
    private int cacheExpire;


    /**
     * 创建token对象
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        // 转化为HttpServletRequest
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 从请求获取token令牌
        String token = getRequestToken(request);
        if (StrUtil.isBlank(token)) {
            return null;
        }
        // 封装成Token对象
        return new OAuth2Token(token);
    }

    /**
     * 判断那种请求可以被shiro框架处理
     * options请求不处理:
     * ps: options代表预检请求,不携带参数,只是校验能不能跟服务器跑通,所以不需要拦截,其余的所有请求都需要过滤
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 转化为HttpServletRequest
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        // 判断请求是不是options请求,如果是则直接放行
        // getMethod() 获取请求方法     RequestMethod请求枚举类
        if (servletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        return false;
    }

    /**
     * 校验令牌
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        //响应结果集
        res.setContentType("text/html");
        res.setCharacterEncoding("UTF-8");
        //设置允许跨域
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        // 清空媒介类token
        localToken.clear();
        // 请求头获取token字符串
        String token = getRequestToken(req);
        if (StrUtil.isBlank(token)) {
            // 设置响应编码
            res.setStatus(HttpStatus.SC_UNAUTHORIZED);
            //设置响应内容
            res.getWriter().print("无效的令牌");
            return false;
        }
        //验证内容是否有效
        try {
            jwtUtil.verifierToken(token);
        } catch (TokenExpiredException e) {  // TokenExpiredException: 令牌过期异常
            // 判断redis里面有没有token
            if (redisTemplate.hasKey(token)) {
                // 如果有那么就把旧令牌删除
                redisTemplate.delete(token);
                //获取UserId
                int userId = jwtUtil.getUserId(token);
                //创建新令牌覆盖旧令牌
                token = jwtUtil.createToken(userId);
                //保存到redis     token  用户id   过期时间   过期单位
                redisTemplate.opsForValue().set(token, userId + "", cacheExpire, TimeUnit.DAYS);
                //保存到媒介类
                localToken.setToken(token);
            } else {
                // 如果在缓存也没有找到令牌,那么就重新登陆
                res.setStatus(HttpStatus.SC_UNAUTHORIZED);
                //设置响应内容
                res.getWriter().print("令牌已过期");
                return false;
            }
        } catch (Exception e) {  // JWTDecodeException: 令牌不对
            res.setStatus(HttpStatus.SC_UNAUTHORIZED);
            res.getWriter().print("无效的令牌");
            return false;
        }
        // 令牌正确 继续执行
        return executeLogin(req, res);
    }

    /**
     * 认证失败 触发该方法
     * 用户没有登录或者用户登录失败,覆盖原来的
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        //设置允许跨域
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
        try {
            // 返回认证失败详细信息
            resp.getWriter().print(e.getMessage());
        } catch (IOException ex) {

        }
        return false;
    }

    /**
     *  filter执行
     */
    @Override
    public void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        super.doFilterInternal(request, response, chain);
    }

    /**
     * 获取令牌字符串方法
     */
    private String getRequestToken(HttpServletRequest httpServletRequest) {
        //从请求头获取token
        String token = httpServletRequest.getHeader("token");
        if (StrUtil.isBlank(token)) {
            //如果为空,尝试从请求体获取token
            token = httpServletRequest.getParameter("token");
        }
        return token;
    }
}
