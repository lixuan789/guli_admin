package com.lixuan.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lixuan.service.edu.entity.Teacher;
import com.lixuan.service.edu.entity.vo.TeacherQueryVo;
import com.lixuan.service.edu.mapper.TeacherMapper;
import com.lixuan.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author lixuan
 * @since 2020-09-14
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    /**
     * 根据讲师名称name，讲师头衔level、讲师入驻时间查询
     * @param page
     * @param limit
     * @param teacherQueryVo
     * @return
     */
    @Override
    public IPage<Teacher> selectPage(Long page, Long limit, TeacherQueryVo teacherQueryVo) {
        Page<Teacher> pageParam = new Page<>(page,limit);
        QueryWrapper<Teacher> query = Wrappers.query();
        query.orderByDesc("gmt_create");
//        query.orderByAsc("sort");
        if (teacherQueryVo==null){
            return baseMapper.selectPage(pageParam,query);
        }
        String name = teacherQueryVo.getName();
        Integer level = teacherQueryVo.getLevel();
        String begin = teacherQueryVo.getJoinDateBegin();
        String end = teacherQueryVo.getJoinDateEnd();

        if (!StringUtils.isEmpty(name)) {
            //左%会使索引失效
            query.likeRight("name", name);
        }

        if (level != null) {
            query.eq("level", level);
        }

        if (!StringUtils.isEmpty(begin)) {
            query.ge("join_date", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            query.le("join_date", end);
        }

        return baseMapper.selectPage(pageParam, query);
    }

    @Override
    public Map<String, Object> listPageWeb(Long page, Long limit) {
        Page<Teacher> teacherPage = new Page<>(page, limit);
        QueryWrapper<Teacher> query = Wrappers.<Teacher>query();
        query.orderByDesc("id");
        baseMapper.selectPage(teacherPage,query);
        List<Teacher> teacherList = teacherPage.getRecords();
        long total = teacherPage.getTotal();
        long current = teacherPage.getCurrent();
        long size = teacherPage.getSize();
        long pages = teacherPage.getPages();
        boolean hasPrevious = teacherPage.hasPrevious();
        boolean hasNext = teacherPage.hasNext();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", teacherList);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }
}
