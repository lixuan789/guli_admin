package com.lixuan.order.service.impl;

import com.lixuan.common.base.result.Result;
import com.lixuan.order.client.EduClient;
import com.lixuan.order.client.UcenterClient;
import com.lixuan.order.entity.CourseWebVo;
import com.lixuan.order.entity.Member;
import com.lixuan.order.entity.Order;
import com.lixuan.order.mapper.OrderMapper;
import com.lixuan.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lixuan.order.util.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author lixuan
 * @since 2020-10-13
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String saveOrder(String courseId, String memberId) {

        CourseWebVo courseInfo = eduClient.getCourseInfo(courseId);

        Member member = ucenterClient.getInfo(memberId);

        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfo.getTitle());
        order.setCourseCover(courseInfo.getCover());
        order.setTeacherName(courseInfo.getTeacherName());
        order.setTotalFee(courseInfo.getPrice());
        order.setMemberId(memberId);
        order.setMobile(member.getMobile());
        order.setNickname(member.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
