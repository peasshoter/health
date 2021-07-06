package com.itheima.test;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class tencent {
    public static void main(String[] args) throws IOException {
// 1 初始化用户身份信息（secretId, secretKey）。
        String secretId = "AKIDXx71eGcgU1II2WjfKiwvkOp34phMlumN";
        String secretKey = "LPIvHMBj12mFSO4TbS1KL6bqoukKcePs";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
// 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("ap-guangzhou");
        ClientConfig clientConfig = new ClientConfig(region);
// 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        List<Bucket> buckets = cosClient.listBuckets();
        for (Bucket bucketElement : buckets) {
            String bucketName = bucketElement.getName();
            String bucketLocation = bucketElement.getLocation();
            System.out.println(bucketName+bucketLocation);
        }
        File localFile = new File("E:\\笔记\\就业班2.1笔记源码-压缩版\\阶段4：会员版(2.1)-医疗实战-传智健康\\03a36073-a140-4942-9b9b-712cecb144901.jpg");
// 指定文件将要存放的存储桶
        String bucketName = "heima-1251402755";
// 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String key = "exampleobject/03a36073-a140-4942-9b9b-712cecb144901.jpg";//文件名
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
    }
}
