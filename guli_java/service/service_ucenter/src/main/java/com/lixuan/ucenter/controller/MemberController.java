package com.lixuan.ucenter.controller;


import com.lixuan.common.base.result.Result;
import com.lixuan.common.base.util.JwtUtils;
import com.lixuan.ucenter.entity.LoginVo;
import com.lixuan.ucenter.entity.Member;
import com.lixuan.ucenter.entity.RegisterVo;
import com.lixuan.ucenter.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author lixuan
 * @since 2020-10-10
 */
@RestController
@RequestMapping("/ucenter/member")
@CrossOrigin
public class MemberController {
    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo) {
        String token = memberService.login(loginVo);
        return Result.ok().data("token", token);
    }

    @ApiOperation(value = "会员注册")
    @PostMapping("register")
    public Result register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return Result.ok();
    }

    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("auth/getLoginInfo")
    public Result getLoginInfo(HttpServletRequest request){
        try {
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            System.out.println(memberId);
            Member member = memberService.getLoginInfo(memberId);
            return Result.ok().data("item", member);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error();
        }
    }

    //根据token字符串获取用户信息
    @PostMapping("getInfoUc/{id}")
    public Member getInfo(@PathVariable String id) {
        //根据用户id获取用户信息
        Member member = memberService.getById(id);
        return member;
    }

    @GetMapping(value = "countRegister/{day}")
    public Result registerCount(@PathVariable String day){
        Integer count = memberService.countRegisterByDay(day);
        return Result.ok().data("countRegister", count);
    }


}

