package com.zzy.order.client;

import com.zzy.order.service.impl.EduClientImpl;
import com.zzy.utils.orderVo.CourseOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Description 远程调用edu服务
 * @Author Zzy
 * @Date 2021/2/26
 */
@Component
@FeignClient(name = "service-edu",fallback = EduClientImpl.class)
public interface EduClient {

    //根据id查询课程信息
    @PostMapping("/edu/courseFront/getCourseInfoOrder/{id}")
    public CourseOrderVo getCourseInfoOrder(@PathVariable("id") String id);
}
