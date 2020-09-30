package com.lixuan.oss.controller;


import com.lixuan.common.base.result.Result;
import com.lixuan.oss.service.OssService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api("OSS模块")
@RestController
@RequestMapping("/api/oss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像的方法
    @PostMapping("upload")
    public Result uploadOssFile(MultipartFile file) {
        //获取上传文件  MultipartFile
        //返回上传到oss的路径
        String url = ossService.uploadFile(file);
        System.out.println(url);
        return Result.ok().data("url",url);
    }

    @DeleteMapping("detele")
    public Result deleteOssFile(String filename){
        ossService.deteleFile(filename);
        return Result.ok();
    }
}
