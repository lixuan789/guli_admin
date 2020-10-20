package com.lixuan.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lixuan.common.base.result.Result;
import com.lixuan.common.base.util.JwtUtils;
import com.lixuan.order.entity.Order;
import com.lixuan.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author lixuan
 * @since 2020-10-13
 */
@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 根据课程id和用户id创建订单，返回订单id
     * @param courseId
     * @param request
     * @return
     */
    @PostMapping("createOrder/{courseId}")
    public Result save(@PathVariable String courseId, HttpServletRequest request) {
        String orderId = orderService.saveOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return Result.ok().data("orderId", orderId);
    }

    /**
     * 根据id获取订单信息接口
     * @param orderNo
     * @return
     */
    @GetMapping("getOrder/{orderNo}")
    public Result get(@PathVariable String orderNo) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);
        return Result.ok().data("item", order);
    }

    /**
     * 根据用户id和课程id查询订单信息
     * @param memberId
     * @param courseId
     * @return
     */
    @GetMapping("isBuyCourse/{memberId}/{courseId}")
    public boolean isBuyCourse(@PathVariable String memberId,
                               @PathVariable String courseId) {
        //订单状态是1表示支付成功
        int count = orderService.count(new QueryWrapper<Order>().eq("member_id", memberId).eq("course_id", courseId).eq("status", 1));
        if(count>0) {
            return true;
        } else {
            return false;
        }
    }
}

