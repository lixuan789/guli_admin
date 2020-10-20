package com.lixuan.order.service;

import com.lixuan.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author lixuan
 * @since 2020-10-13
 */
public interface OrderService extends IService<Order> {

    String saveOrder(String courseId, String memberId);
}
