package com.lixuan.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.lixuan.vod.service.VideoService;
import com.lixuan.vod.utils.ConstantPropertiesUtil;
import com.lixuan.vod.utils.VodUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
    @Override
    public String uploadVideo(MultipartFile file) {
        String accessKeyId= ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret=ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String fileName=file.getOriginalFilename();
        String title=fileName.substring(0,fileName.lastIndexOf('.'));
        InputStream inputStream = null;
        String VideoId=null;
        try {
            inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                VideoId=response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                VideoId=response.getVideoId();
            }
            return VideoId;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteVideo(String videoId) {
        String accessKeyId= ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret=ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        try {
            DefaultAcsClient client = VodUtil.initVodClient(accessKeyId, accessKeySecret);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            client.getAcsResponse(request);
        }catch (ClientException e){
            e.printStackTrace();
        }

    }

    @Override
    public void removeVideoList(List<String> videoList) {
        String accessKeyId= ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret=ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        try {
            DefaultAcsClient client = VodUtil.initVodClient(accessKeyId, accessKeySecret);
            DeleteVideoRequest request = new DeleteVideoRequest();
            String join = StringUtils.join(videoList, ",");
            request.setVideoIds(join);
            client.getAcsResponse(request);
        }catch (ClientException e){
            e.printStackTrace();
        }
    }
}
