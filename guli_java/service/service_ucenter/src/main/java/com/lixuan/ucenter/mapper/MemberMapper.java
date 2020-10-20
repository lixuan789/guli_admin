package com.lixuan.ucenter.mapper;

import com.lixuan.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author lixuan
 * @since 2020-10-10
 */
@Repository
public interface MemberMapper extends BaseMapper<Member> {

    Integer countRegisterByDay(String day);
}
