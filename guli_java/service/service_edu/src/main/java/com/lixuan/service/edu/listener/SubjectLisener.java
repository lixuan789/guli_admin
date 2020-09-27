package com.lixuan.service.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lixuan.service.edu.entity.Subject;
import com.lixuan.service.edu.entity.excel.SubjectData;
import com.lixuan.service.edu.service.SubjectService;

public class SubjectLisener extends AnalysisEventListener<SubjectData> {

    private SubjectService subjectService;

    public SubjectLisener() {
    }

    public SubjectLisener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * 一行一行读Excel文件
     * @param subjectData
     * @param analysisContext
     */
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData==null){
            throw new RuntimeException("Excel数据为空");
        }
        Subject oneSubject = this.existSubject(subjectData.getOneSubjectName(), "0");
        if (oneSubject==null){
            oneSubject = new Subject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectData.getOneSubjectName());
            this.subjectService.save(oneSubject);
        }

        String pid = oneSubject.getId();
        Subject twoSubject = this.existSubject(subjectData.getTwoSubjectName(), pid);
        if (twoSubject==null){
            twoSubject = new Subject();
            twoSubject.setTitle(subjectData.getTwoSubjectName());
            twoSubject.setParentId(pid);
            this.subjectService.save(twoSubject);
        }
    }

    private Subject existSubject(String name, String parentId) {
        QueryWrapper<Subject> query = Wrappers.query();
        query.eq("title",name).eq("parent_id",parentId);
        Subject one = this.subjectService.getOne(query);
        return one;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
