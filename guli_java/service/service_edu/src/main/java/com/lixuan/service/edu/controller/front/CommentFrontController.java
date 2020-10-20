package com.lixuan.service.edu.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lixuan.common.base.result.Result;
import com.lixuan.common.base.util.JwtUtils;
import com.lixuan.service.edu.client.UcenterClient;
import com.lixuan.service.edu.entity.Comment;
import com.lixuan.service.edu.entity.Member;
import com.lixuan.service.edu.service.CommentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
 * @author lixuan
 * @since 2020-09-14
 */
@RestController
@RequestMapping("/admin/comment/front")
@CrossOrigin
public class CommentFrontController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UcenterClient ucenterClient;

    //根据课程id查询评论列表
    @ApiOperation(value = "评论分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                    String courseId) {
        Page<Comment> pageParam = new Page<>(page, limit);

        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);

        commentService.page(pageParam,wrapper);
        List<Comment> commentList = pageParam.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());
        return Result.ok().data(map);
    }

    @ApiOperation(value = "添加评论")
    @PostMapping("save")
    public Result save(@RequestBody Comment comment, HttpServletRequest request) {

        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)) {
            return Result.error().code(28004).message("请登录");
        }
        comment.setMemberId(memberId);

        Member member = ucenterClient.getInfo(memberId);

        comment.setNickname(member.getNickname());
        comment.setAvatar(member.getAvatar());

        commentService.save(comment);
        return Result.ok();
    }
}

