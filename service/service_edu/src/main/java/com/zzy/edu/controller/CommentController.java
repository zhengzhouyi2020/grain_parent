package com.zzy.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzy.edu.client.UcenterClient;
import com.zzy.edu.entity.Comment;
import com.zzy.edu.entity.vo.UcMemberVo;
import com.zzy.edu.service.CommentService;
import com.zzy.utils.JwtUtils;
import com.zzy.utils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation(value = "评论分页列表")
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
    @ApiOperation(value = "添加评论")
    public R save(@RequestBody Comment comment, HttpServletRequest request){
        String memberId= JwtUtils.getMemberIdByJwtToken(request);

        if(StringUtils.isEmpty(memberId)){
            return R.error("code不能为空!").code(0).message("请登录！");
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

    @GetMapping("/list/{page}/{limit}")
    @ApiOperation(value = "后台获取评论列表")
    public R list(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit){

        Page<Comment> pageParam = new Page<>(page, limit);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        commentService.page(pageParam,wrapper);

        return R.ok().data("list",pageParam.getRecords()).data("total",pageParam.getTotal());
    }

    @PostMapping("/add")
    @ApiOperation(value = "后台添加评论")
    public R add(@RequestBody Comment comment){
        if (commentService.save(comment)){
            return R.ok();
        }
        return R.error("code不能为空!");
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除评论")
    public R delete(@PathVariable("id") Integer id){
        if (commentService.removeById(id)){
            return R.ok();
        }
        return R.error("code不能为空!");
    }

    @PostMapping ("/update")
    @ApiOperation(value = "后台修改评论")
    public R update(@RequestBody Comment comment) {
        if (commentService.saveOrUpdate(comment)){
            return R.ok();
        }
        return R.error("code不能为空!");
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "后台获取评论")
    public R getInfo(@PathVariable long id){

        Comment comment = commentService.getById(id);
        if (comment == null) {
            return R.error("code不能为空!");
        }
        return R.ok().data("item",comment);
    }

    @GetMapping("/getAllInfo")
    @ApiOperation(value = "后台评论课程详情")
    public R getCourseInfo(){

        return R.ok();
    }

}

