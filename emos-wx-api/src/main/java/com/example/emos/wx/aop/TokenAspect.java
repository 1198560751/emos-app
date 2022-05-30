package com.example.emos.wx.aop;

import com.example.emos.wx.common.util.R;
import com.example.emos.wx.config.shiro.ThreadLocalToken;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TokenAspect {

    @Autowired
    private ThreadLocalToken threadLocalToken;

    // 拦截   controller下面所有方法下面所有参数全部拦截
    @Pointcut("execution(public * com.example.emos.wx.controller.*.*(..)))")
    public void aspect() { }

    /**
     * 环绕通知
     */
    @Around("aspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //获得拦截方法返回结果
        R r = (R) point.proceed();
        String token = threadLocalToken.getToken();
        if (token!=null) {   // 检查是否有新生成令牌
            r.put("token", token);
            threadLocalToken.clear();
        }
        return r;
    }
}
