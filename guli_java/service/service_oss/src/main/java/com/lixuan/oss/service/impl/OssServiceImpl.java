package com.lixuan.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.lixuan.oss.service.OssService;
import com.lixuan.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    /**
     * 上传文件到Oss
     * @param file
     * @return
     */
    @Override
    public String uploadFile(MultipartFile file) {
        // 工具类获取值
        String endpoint = ConstantPropertiesUtils.END_POIND;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            // 创建OSS实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            if (!ossClient.doesBucketExist(bucketName)){//不存在则创建
                //创建bucket
                ossClient.createBucket(bucketName);
                //设置oss实例的访问权限：公共读
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }

            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String fileName = file.getOriginalFilename();
            System.out.println(fileName);

            //1 在文件名称里面添加随机唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            // yuy76t5rew01.jpg
            fileName = uuid+fileName;

            //2 把文件按照日期进行分类
            //获取当前日期
            //   2019/11/12
            String datePath = new DateTime().toString("yyyy/MM/dd");
            //拼接
            //  2019/11/12/ewtqr313401.jpg
            fileName = datePath+"/"+fileName;

            //调用oss方法实现上传
            //第一个参数  Bucket名称
            //第二个参数  上传到oss文件路径和文件名称   aa/bb/1.jpg
            //第三个参数  上传文件输入流
            ossClient.putObject(bucketName,fileName , inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传之后文件路径返回
            //需要把上传到阿里云oss路径手动拼接出来
            //  https://edu-guli-1010.oss-cn-beijing.aliyuncs.com/01.jpg
            String url = "https://"+bucketName+"."+endpoint+"/"+fileName;
            return url;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deteleFile(String filename) {
        String endpoint = ConstantPropertiesUtils.END_POIND;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        System.out.println(filename);
        String[] split = filename.split("/");
        String a="";
        for (int i=3;i<split.length;i++){
            if (i!=split.length-1){
                a=a+split[i]+"/";
            }else {
                a=a+split[i];
            }
        }
        client.deleteObject(bucketName,a);
        client.shutdown();
    }
}
