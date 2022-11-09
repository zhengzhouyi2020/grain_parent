package com.zzy.order.service;

import com.zzy.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author zzy
 * @since 2021-02-26
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, String memberId);
}
