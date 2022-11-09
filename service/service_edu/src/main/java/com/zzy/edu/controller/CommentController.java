package com.zzy.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzy.edu.client.UcenterClient;
import com.zzy.edu.entity.Comment;
import com.zzy.edu.entity.vo.UcMemberVo;
import com.zzy.edu.service.CommentService;
import com.zzy.utils.JwtUtils;
import com.zzy.utils.QueryPage;
import com.zzy.utils.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author zzy
 * @since 2021-02-25
 */
@RestController
@RequestMapping("/edu/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UcenterClient ucenterClient;

    //根据课程id查询评论列表

    @GetMapping("/{pageNo}/{pageSize}")
    public R index(@PathVariable Long pageNo,@PathVariable Long pageSize,String courseId){
        Page<Comment> commentPage=new Page<>(pageNo,pageSize);
        QueryWrapper<Comment> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.orderByDesc("gmt_create");

        commentService.page(commentPage,wrapper);
        List<Comment> commentList=commentPage.getRecords();
        Map<String,Object> map=new HashMap<>();
        map.put("items", commentList);
        map.put("current", commentPage.getCurrent());
        map.put("pages", commentPage.getPages());
        map.put("size", commentPage.getSize());
        map.put("total", commentPage.getTotal());
        map.put("hasNext", commentPage.hasNext());
        map.put("hasPrevious", commentPage.hasPrevious());
        return R.ok().data(map);

    }

    @PostMapping("/auth/save")
    public R save(@RequestBody Comment comment, HttpServletRequest request){
        String memberId= JwtUtils.getMemberIdByJwtToken(request);

        if(StringUtils.isEmpty(memberId)){
            return R.error().code(0).message("请登录！");
        }
        comment.setId(memberId);

        UcMemberVo ucMemberVo=ucenterClient.getInfo(memberId);

        System.out.println(ucMemberVo.getNickname());
        System.out.println(ucMemberVo.getAvatar());

        comment.setNickname(ucMemberVo.getNickname());
        comment.setAvatar(ucMemberVo.getAvatar());

        commentService.save(comment);
        return R.ok();


    }

}

