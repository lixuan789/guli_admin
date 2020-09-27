package com.lixuan.service.edu.controller.admin;


import com.lixuan.common.base.result.Result;
import com.lixuan.service.edu.entity.Video;
import com.lixuan.service.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author lixuan
 * @since 2020-09-14
 */
@RestController
@RequestMapping("/admin/edu/video")
@CrossOrigin
public class VideoController {

    @Autowired
    private VideoService videoService;

    /**
     * 获取所有视频小节
     * @return
     */
    @GetMapping("getAllVideo")
    public Result getAllVideo(){
        List<Video> list=videoService.getAllVideo();
        return Result.ok().data("list",list);
    }

    /**
     * 根据id获取某一个小节
     * @param id
     * @return
     */
    @GetMapping("getVideo/{id}")
    public Result getVideo(@PathVariable("id") String id){
        Video video=videoService.getVideo(id);
        return Result.ok().data("item",video);
    }

    /**
     * 增加小节
     * @param video
     * @return
     */
    @PostMapping("addVideo")
    public Result addVideo(@RequestBody(required = true) Video video){
        videoService.addVideo(video);
        return Result.ok();
    }

    /**
     * 修改小节
     * @param video
     * @return
     */
    @PutMapping("updateVideo")
    public Result updateVideo(@RequestBody(required = true) Video video){
        videoService.updateVideo(video);
        return Result.ok();
    }

    @DeleteMapping("deleteVideo/{id}")
    public Result deleteVideo(@PathVariable("id") String id){
        videoService.deleteVideo(id);
        return Result.ok();
    }

}

