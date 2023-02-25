package com.zzy.order.service.impl;

import com.zzy.order.entity.Order;
import com.zzy.order.mapper.OrderMapper;
import com.zzy.order.client.EduClient;
import com.zzy.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.order.client.UCenterClient;
import com.zzy.order.utils.OrderNoUtil;
import com.zzy.utils.orderVo.CourseOrderVo;
import com.zzy.utils.orderVo.UcenterMemberOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2021-02-26
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {


    @Autowired
    private EduClient eduClient;

    @Qualifier("UCenterClient")
    @Autowired
    private UCenterClient uCenterClient;

    //生成订单的方法

    @Override
    public String createOrder(String courseId, String memberId) {
        // 通过远程调用根据用户id获取用户信息
        UcenterMemberOrder ucMemberVo = uCenterClient.getInfo(memberId);

        // 通过远程调用根据课程id获取课程信息
        CourseOrderVo courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        // 创建订单
        // 创建TOrder对象
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(ucMemberVo.getMobile());
        order.setNickname(ucMemberVo.getNickname());
        order.setStatus(0); // 支付类型，默认1：微信支付
        order.setPayType(1); // 支付状态，默认0：未支付
        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
