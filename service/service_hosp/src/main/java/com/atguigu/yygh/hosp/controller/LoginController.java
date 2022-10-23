package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.attguigu.abcommon.config.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author zzj
 * @date 2022/10/19
 */
@Api(tags = "登录接口")
@RestController
@CrossOrigin
@RequestMapping("/hosp")
public class LoginController {

    @PostMapping("/user/login")
    @ApiOperation(value = "登录")
    public R login(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("token","admin_token");
        return R.ok().data(map);
    }

    @GetMapping("/user/info")
    @ApiOperation("获取登录信息")
    public R getInfo(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("roles","admin");
        map.put("introduction","I am a super administrator");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name","管理员");
        return R.ok().data(map);
    }


}
