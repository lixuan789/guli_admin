package com.lixuan.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lixuan.service.edu.entity.Chapter;
import com.lixuan.service.edu.entity.Video;
import com.lixuan.service.edu.mapper.ChapterMapper;
import com.lixuan.service.edu.mapper.VideoMapper;
import com.lixuan.service.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lixuan
 * @since 2020-09-14
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
    @Autowired
    private VideoMapper videoMapper;

    /**
     * 获取课程所有的大纲
     * @return
     * @param courseId
     */
    @Override
    public List<Chapter> getAllChapter(String courseId) {
        QueryWrapper<Chapter> query = Wrappers.<Chapter>query();
        query.eq("course_id",courseId).orderByAsc("id");
        List<Chapter> chapterList = this.list(query);
        for (Chapter chapter:chapterList){
            QueryWrapper<Video> videoQuery = Wrappers.<Video>query();
            videoQuery.eq("course_id",courseId)
                    .eq("chapter_id",chapter.getId())
                    .orderByAsc("id");
            List<Video> videoList = videoMapper.selectList(videoQuery);
            chapter.setChildren(videoList);
        }
        return chapterList;
    }

    @Override
    public void addChapter(Chapter chapter) {
        baseMapper.insert(chapter);
    }

    @Override
    public void updateChapter(Chapter chapter) {
        baseMapper.updateById(chapter);
    }

    @Override
    public void deleteChapter(String id) {
        QueryWrapper<Video> query = Wrappers.<Video>query();
        query.eq("chapter_id",id);
        Integer count = videoMapper.selectCount(query);//先查看是否有小结
        if (count>0){
            throw new RuntimeException("有小节不可以删除");
        }else {
            baseMapper.deleteById(id);
        }
    }

    @Override
    public Chapter getChapter(String id) {
        Chapter chapter = baseMapper.selectById(id);
        return chapter;
    }
}
