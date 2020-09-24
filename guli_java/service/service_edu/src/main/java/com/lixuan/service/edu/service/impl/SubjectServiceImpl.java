package com.lixuan.service.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.lixuan.service.edu.entity.Subject;
import com.lixuan.service.edu.entity.SubjectData;
import com.lixuan.service.edu.listener.SubjectLisener;
import com.lixuan.service.edu.mapper.SubjectMapper;
import com.lixuan.service.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
}
