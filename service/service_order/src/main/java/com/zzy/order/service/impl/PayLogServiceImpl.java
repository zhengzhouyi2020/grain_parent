package com.zzy.order.service.impl;

import com.zzy.order.entity.PayLog;
import com.zzy.order.mapper.PayLogMapper;
import com.zzy.order.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2021-02-26
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

}
