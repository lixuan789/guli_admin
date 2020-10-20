package com.lixuan.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lixuan.common.base.result.Result;
import com.lixuan.common.base.util.JwtUtils;
import com.lixuan.common.base.util.MD5;
import com.lixuan.ucenter.entity.LoginVo;
import com.lixuan.ucenter.entity.Member;
import com.lixuan.ucenter.entity.RegisterVo;
import com.lixuan.ucenter.mapper.MemberMapper;
import com.lixuan.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author lixuan
 * @since 2020-10-10
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //校验参数
        if (StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            return null;
        }
        QueryWrapper<Member> query = Wrappers.<Member>query();
        query.eq("mobile",mobile);
        Member member = memberMapper.selectOne(query);
        //是否存在
        if (member==null){
            return null;
        }
        //密码是否正确
        if (!MD5.encrypt(password).equals(member.getPassword())){
            return null;
        }
        //是否禁用
        if (member.getDisabled()){
            return null;
        }
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        if (StringUtils.isEmpty(code)||StringUtils.isEmpty(mobile)||StringUtils.isEmpty(nickname)||StringUtils.isEmpty(password)){
            return;
        }

        String mobleCode = redisTemplate.opsForValue().get(mobile);
        //验证码是否正确
        if (!code.equals(mobleCode)){
            return;
        }

        QueryWrapper<Member> query = Wrappers.<Member>query();
        query.eq("mobile",mobile);
        Integer count = memberMapper.selectCount(query);
        //是否已存在该手机号
        if (count>0){
            return;
        }

        Member member = new Member();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setDisabled(false);
        memberMapper.insert(member);
    }

    @Override
    public Member getLoginInfo(String memberId) {
        Member member = memberMapper.selectById(memberId);;
        return member;
    }

    @Override
    public Member getByOpenid(String openid) {
        QueryWrapper<Member> query = Wrappers.<Member>query();
        query.eq("openid",openid);
        Member member = memberMapper.selectOne(query);
        return member;
    }

    @Override
    public Integer countRegisterByDay(String day) {
        return memberMapper.countRegisterByDay(day);
    }
}
