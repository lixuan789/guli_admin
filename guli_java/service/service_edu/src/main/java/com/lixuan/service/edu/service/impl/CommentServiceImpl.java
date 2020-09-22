package com.lixuan.service.edu.service.impl;

import com.lixuan.service.edu.entity.Comment;
import com.lixuan.service.edu.mapper.CommentMapper;
import com.lixuan.service.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author lixuan
 * @since 2020-09-14
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
