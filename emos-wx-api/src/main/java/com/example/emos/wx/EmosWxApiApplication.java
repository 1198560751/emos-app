package com.example.emos.wx;

import cn.hutool.core.util.StrUtil;
import com.example.emos.wx.config.SystemConstants;
import com.example.emos.wx.db.dao.SysConfigDao;
import com.example.emos.wx.db.pojo.SysConfig;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.List;

@SpringBootApplication
@MapperScan("com.example.emos.wx.db.dao")
@Slf4j
@ServletComponentScan
public class EmosWxApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmosWxApiApplication.class, args);
    }


    @Autowired
    private SysConfigDao sysConfigDao;

    @Autowired
    private SystemConstants systemConstants;

    /**
     * 初始化 考勤参数 做缓存
     * PostConstruct : 注解表示在@Autowired执行之后
     */
    @PostConstruct
    public void init() {
        List<SysConfig> sysConfigs = sysConfigDao.selectAllParam();
        sysConfigs.forEach(item -> {
            String paramKey = item.getParamKey();
            paramKey = StrUtil.toCamelCase(paramKey);  //转换为驼峰命名法
            String paramValue = item.getParamValue();
            //通过反射赋值
            try {
                Field field = systemConstants.getClass().getDeclaredField(paramKey);
                field.set(systemConstants, paramValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error("执行异常", e);
            }
        });
    }

}
