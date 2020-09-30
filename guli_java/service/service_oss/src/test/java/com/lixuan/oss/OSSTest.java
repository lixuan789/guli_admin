package com.lixuan.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.lixuan.oss.utils.ConstantPropertiesUtils;
import org.bouncycastle.util.Strings;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OSSTest {



    @Test
    public void testCreateBucket() {

        String endpoint= ConstantPropertiesUtils.END_POIND;
        String accessKeyId=ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret=ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName=ConstantPropertiesUtils.BUCKET_NAME;
        // 创建OSSClient实例。
//        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建存储空间。
        ossClient.createBucket(bucketName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
    @Test
    public void testExist() {

        String endpoint= ConstantPropertiesUtils.END_POIND;
        String accessKeyId=ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret=ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName=ConstantPropertiesUtils.BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        boolean exists = ossClient.doesBucketExist(bucketName);
        System.out.println(exists);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    public void test2(){
        String filename="https://lixuan-file.oss-cn-beijing.aliyuncs.com/2020/09/25/3482fe240e6f46b08afb577ae859137bcover.jpg";
        String[] split = filename.split("/")[1].split("/");
        String a="";
        for (int i=2;i<split.length;i++){
            if (i!=split.length-1){
                a=a+split[i]+"/";
            }else {
                a=a+split[i];
            }
        }
        System.out.println(a);
    }
}
