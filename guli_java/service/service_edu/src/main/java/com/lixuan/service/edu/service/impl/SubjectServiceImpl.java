package com.lixuan.service.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lixuan.service.edu.entity.Subject;
import com.lixuan.service.edu.entity.excel.SubjectData;
import com.lixuan.service.edu.entity.subject.OneSubject;
import com.lixuan.service.edu.entity.subject.TwoSubject;
import com.lixuan.service.edu.listener.SubjectLisener;
import com.lixuan.service.edu.mapper.SubjectMapper;
import com.lixuan.service.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author lixuan
 * @since 2020-09-14
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {


    @Override
    public void saveSubject(MultipartFile file, SubjectService subjectService) {
        try {
            EasyExcel.read(file.getInputStream(), SubjectData.class,new SubjectLisener(this)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllSubject() {
        List<OneSubject> list=new ArrayList<>();
        QueryWrapper<Subject> query = Wrappers.<Subject>query();
        query.eq("parent_id","0");
        List<Subject> oneSubjectList = this.list(query);

        for (Subject one:oneSubjectList){
            OneSubject oneSubject = new OneSubject();
            oneSubject.setId(one.getId());
            oneSubject.setTitle(one.getTitle());

            query.clear();
            query.eq("parent_id",oneSubject.getId());
            List<Subject> twoSubjectList = this.list(query);//获取一级分类下的所有二级分类
            List<TwoSubject> children=new ArrayList<>();
            for (Subject two:twoSubjectList){
                TwoSubject twoSubject = new TwoSubject();
                twoSubject.setId(two.getId());
                twoSubject.setTitle(two.getTitle());
                children.add(twoSubject);
            }
            oneSubject.setChildren(children);
            list.add(oneSubject);
        }
        return list;
    }

    @Override
    public List<Subject> getAllSubject1() {
        List<Subject> list = this.list();
        return parseMenuTree(list);
    }

    private List<Subject> parseMenuTree(List<Subject> list) {
        List<Subject> result = new ArrayList<>();

        // 1、获取第一级节点
        for (Subject subject : list) {
            if(subject.getParentId().equals("0")) {
                result.add(subject);
            }
        }

        // 2、递归获取子节点
        for (Subject parent : result) {
            parent = recursiveTree(parent, list);
        }
        return result;
    }

    private Subject recursiveTree(Subject parent, List<Subject> list) {
        for (Subject subject:list){
            if (parent.getId().equals(subject.getParentId())){
                subject=recursiveTree(subject,list);
                parent.getChildren().add(subject);
            }
        }
        return parent;
    }
}
