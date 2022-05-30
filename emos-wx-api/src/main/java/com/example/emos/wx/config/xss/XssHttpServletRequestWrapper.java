package com.example.emos.wx.config.xss;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.json.JSONUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 通过覆盖原来的请求对象,抵制Xss攻击
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }


    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        //如果不为空则过滤一下
        if (!StrUtil.hasEmpty(value)) {
            value = HtmlUtil.filter(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                if (!StrUtil.hasEmpty(value)) {
                    value = HtmlUtil.filter(value);
                }
                values[i] = value;
            }
        }
        return values;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameterMap = super.getParameterMap();
        LinkedHashMap<String, String[]> map = new LinkedHashMap<>();
        if (parameterMap != null) {
            for (String key : parameterMap.keySet()) {
                String[] values = parameterMap.get(key);
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    if (!StrUtil.hasEmpty(value)) {
                        value = HtmlUtil.filter(value);
                    }
                    values[i] = value;
                }
                map.put(key, values);
            }
        }
        return map;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (!StrUtil.hasEmpty(value)) {
            value = HtmlUtil.filter(value);
        }
        return value;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ServletInputStream in = super.getInputStream();
        InputStreamReader reader = new InputStreamReader(in,Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer body = new StringBuffer();
        String line = bufferedReader.readLine();
        while (line != null) {
            body.append(line);
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        reader.close();
        in.close();
        // 将JSON格式转换为Map
        Map<String, Object> map = JSONUtil.parseObj(body.toString());
        // 对Map的结果进行转移
        Map<String, Object> result = new LinkedHashMap<>();
        for (String key : map.keySet()) {
            Object val = map.get(key);
            // 判断是不是字符串格式数据,如果是则转移
            if (val instanceof String) {
                if (!StrUtil.hasEmpty(val.toString())) {
                    result.put(key, HtmlUtil.filter(val.toString()));
                }
            } else {
                result.put(key, val);
            }
        }
        String json = JSONUtil.toJsonStr(result);
        ByteArrayInputStream ba = new ByteArrayInputStream(json.getBytes());
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return ba.read();
            }
        };
    }
}
