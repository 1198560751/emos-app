package com.example.emos.wx.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.example.emos.wx.common.util.R;
import com.example.emos.wx.config.shiro.JwtUtil;
import com.example.emos.wx.controller.form.CheckinForm;
import com.example.emos.wx.exception.EmosException;
import com.example.emos.wx.service.CheckinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.CredentialNotFoundException;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

@RequestMapping("/checkIn")
@RestController
@Api("签到模块web接口")
@Slf4j
public class CheckinController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CheckinService checkinService;

    @Value("${emos.image-folder}")
    private String imageFolder;


    @GetMapping("/validCanCheckIn")
    @ApiOperation("查看用户今天是否可以签到")
    public R validCanCheckIn(@RequestHeader("token") String token) {
        int userId = jwtUtil.getUserId(token);        // 从令牌解析userId
        String result = checkinService.validCanCheckIn(userId, DateUtil.today());
        return R.ok(result);
    }

    @PostMapping("/checkin")
    @ApiOperation("签到")
    public R checkin(@Valid CheckinForm form, @RequestParam("photo") MultipartFile file, @RequestHeader("token") String token) {
        if (file == null) {
            R.error("请上传人脸头像");
        }
        int userId = jwtUtil.getUserId(token);
        String fileName = file.getOriginalFilename().toLowerCase();
        if (!fileName.endsWith(".jpg")) {
            R.error("请上传jpg文件");
        }
        String path = imageFolder + "/" + fileName;
        try {
            file.transferTo(Paths.get(path));  //存放文件
            HashMap<Object, Object> map = new HashMap<>();
            map.put("userId", userId);
            map.put("path", path);
            map.put("city", form.getCity());
            map.put("district", form.getDistrict());
            map.put("address", form.getAddress());
            map.put("country", form.getCountry());
            map.put("province", form.getProvince());
            checkinService.checkin(map);
            return R.ok("签到成功");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new EmosException("图片保存失败");
        } finally {
            FileUtil.del(path); //把自拍照片删除
        }

    }


    @PostMapping("/createFaceModel")
    @ApiOperation("创建人脸模型")
    public R createFaceModel(@RequestParam("photo") MultipartFile file, @RequestHeader("token") String token) {
        return null;
    }
}
