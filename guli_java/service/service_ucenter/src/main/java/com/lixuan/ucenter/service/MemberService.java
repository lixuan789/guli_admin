package com.lixuan.ucenter.service;

import com.lixuan.ucenter.entity.LoginVo;
import com.lixuan.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lixuan.ucenter.entity.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author lixuan
 * @since 2020-10-10
 */
public interface MemberService extends IService<Member> {

    /**
     * 登录
     * @param loginVo
     * @return
     */
    String login(LoginVo loginVo);

    /**
     * 注册
     * @param registerVo
     */
    void register(RegisterVo registerVo);

    /**
     * 根据id获取会员的信息
     * @param memberId
     * @return
     */
    Member getLoginInfo(String memberId);

    Member getByOpenid(String openid);

    /**
     * 查找某一天的注册人数
     * @param day
     * @return
     */
    Integer countRegisterByDay(String day);
}
