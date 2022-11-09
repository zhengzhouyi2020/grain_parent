package com.zzy.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.order.entity.Order;
import com.zzy.order.service.OrderService;
import com.zzy.utils.JwtUtils;
import com.zzy.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author zzy
 * @since 2021-02-26
 */
@RestController
@RequestMapping("/order/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //生成订单的方法
    @PostMapping("/createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request){

        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        String orderId=orderService.createOrder(courseId,memberId);
        return R.ok().data("orderId",orderId);

    }

    //根据订单查询订单信息接口
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderId);

        Order order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }


}

