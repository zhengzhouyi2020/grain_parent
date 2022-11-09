package com.zzy.edu.service.impl;

import com.zzy.edu.entity.Comment;
import com.zzy.edu.mapper.CommentMapper;
import com.zzy.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2021-02-25
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
