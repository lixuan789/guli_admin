package com.lixuan.service.edu.controller.admin;

import com.lixuan.common.base.result.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
@CrossOrigin
public class LoginController {

    /**
     * 登录
     * @return
     */
    @PostMapping("login")
    public Result login(){
        return Result.ok().data("token","admin");
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("info")
    public Result info(){
        return Result.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    @PostMapping("logout")
    public Result logout(){
        return Result.ok();
    }

}
