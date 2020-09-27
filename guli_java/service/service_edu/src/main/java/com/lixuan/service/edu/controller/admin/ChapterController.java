package com.lixuan.service.edu.controller.admin;


import com.lixuan.common.base.result.Result;
import com.lixuan.service.edu.entity.Chapter;
import com.lixuan.service.edu.service.ChapterService;
import com.sun.xml.bind.v2.TODO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lixuan
 * @since 2020-09-14
 */
@RestController
@RequestMapping("/admin/edu/chapter")
@CrossOrigin
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    /**
     * 获取课程的所有章节
     * @param courseId
     * @return
     */
    @GetMapping("getAllChapter/{courseId}")
    public Result getAllChapter(@PathVariable(value = "courseId",required = true) String courseId){
        List<Chapter> characterList=chapterService.getAllChapter(courseId);
        return Result.ok().data("list",characterList);
    }

    /**
     * 根据id获取章节
     * @param id
     * @return
     */
    @GetMapping("getChapter/{id}")
    public Result getChapter(@PathVariable(value = "id",required = true) String id){
        Chapter chapter=chapterService.getChapter(id);
        return Result.ok().data("item",chapter);
    }

    /**
     * 增加章节
     * @param chapter
     * @return
     */
    @PostMapping("addChapter")
    public Result addChapter(@RequestBody(required = true) Chapter chapter){
        chapterService.addChapter(chapter);
        return Result.ok();
    }

    /**
     * 更新章节
     * @param chapter
     * @return
     */
    @PutMapping("updateChapter")
    public Result updateChapter(@RequestBody(required = true) Chapter chapter){
        chapterService.updateChapter(chapter);
        return Result.ok();
    }

    /**
     * 删除章节，有小节则不可以删除
     * @param id
     * @return
     */
    @DeleteMapping("deleteChapter/{id}")
    public Result deleteChapter(@PathVariable("id") String id){
        chapterService.deleteChapter(id);
        return Result.ok();
    }


}

