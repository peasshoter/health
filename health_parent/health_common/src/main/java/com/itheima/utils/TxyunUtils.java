package com.itheima.utils;

import java.io.*;
import java.util.Random;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.springframework.web.multipart.MultipartFile;

public class TxyunUtils {

    // 申明变量
    public static String QCloudSecretId = "AKIDXx71eGcgU1II2WjfKiwvkOp34phMlumN";
    public static String QCloudSecretKey = "LPIvHMBj12mFSO4TbS1KL6bqoukKcePs";
    public static String bucketName = "heima-1251402755";
    public static String qcloudRegion = "ap-guangzhou";
    public static COSCredentials cred;
    public static COSClient cosClient;
    private static final String prefix = "exampleobject/";

    /**
     * 初始化
     */
    public static void init() {

        //// 1 初始化用户身份信息（secretId, secretKey）
        cred = new BasicCOSCredentials(QCloudSecretId, QCloudSecretKey);
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(qcloudRegion);
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);

        // 3 生成 cos 客户端。
        cosClient = new COSClient(cred, clientConfig);
    }

    /**
     * 关闭客户端
     */
    public static void close() {
        // 关闭客户端(关闭后台线程)
        cosClient.shutdown();
    }

    public static String upload(MultipartFile imgFile) {
        init();
        String originalFilename = imgFile.getOriginalFilename();
//        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") - 1);
//        String filename = UUID.randomUUID().toString() + extension;
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        File localFile = null;
        try {
            localFile = File.createTempFile(String.valueOf(System.currentTimeMillis()), substring);
            imgFile.transferTo(localFile);
            Random random = new Random();
            originalFilename = prefix + random.nextInt(10000) + System.currentTimeMillis() + substring;
            PutObjectRequest objectRequest = new PutObjectRequest(bucketName, originalFilename, localFile);
            cosClient.putObject(objectRequest);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
            return originalFilename;
        }
    }

    public static void DeleteObject(String key) {
        init();
        try {
            cosClient.deleteObject(bucketName, key);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            close();
        }
    }

    public void getAllBucketObject(String prefixPath) {
        init();
        try {
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
            // 设置 bucket 名称
            listObjectsRequest.setBucketName(bucketName);
            // prefix 表示列出的 object 的 key 以 prefix 开始
            listObjectsRequest.setPrefix(prefixPath);
            // 设置最大遍历出多少个对象, 一次 listobject 最大支持1000
            listObjectsRequest.setMaxKeys(1000);
            // deliter表示分隔符, 设置为/表示列出当前目录下的object, 设置为空表示列出所有的object
            listObjectsRequest.setDelimiter("");
            ObjectListing objectListing = cosClient.listObjects(listObjectsRequest);
            // common prefix表示表示被delimiter截断的路径, 如delimter设置为/, common prefix则表示所有子目录的路径
            for (COSObjectSummary cosObjectSummary : objectListing.getObjectSummaries()) {
                // 对象的路径 key
                String key = cosObjectSummary.getKey();
                // 对象的 etag
                String etag = cosObjectSummary.getETag();

                // 对象的长度
                long fileSize = cosObjectSummary.getSize();
                // 对象的存储类型
                String storageClass = cosObjectSummary.getStorageClass();
                System.out.println("filekey:" + key + "; etag:" + etag + "; fileSize:" + fileSize
                        + "; storageClass:" + storageClass + ";" + cosObjectSummary.getBucketName());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            close();
        }
    }


    public PutObjectResult qcloudUpload(File localFile, String fileKey) {
        init();
        PutObjectResult putObjectResult = null;
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileKey, localFile);
            putObjectResult = cosClient.putObject(putObjectRequest);
        } catch (Exception serverException) {
            serverException.printStackTrace();
        } finally {
            close();
        }
        return putObjectResult;
    }

    public PutObjectResult qcloudUploadInput(File localFile, String fileKey) {
        init();
        PutObjectResult putObjectResult = null;
        FileInputStream fileInputStream = null;
        try {
            // 方法2 从输入流上传(需提前告知输入流的长度, 否则可能导致 oom)
            fileInputStream = new FileInputStream(localFile);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            // 设置输入流长度为500
            objectMetadata.setContentLength(500);
            // 设置 Content type, 默认是 application/octet-stream
            //content-type, 对于本地文件上传, 默认根据本地文件的后缀进行映射，如 jpg 文件映射 为image/jpg,可以不设置
            objectMetadata.setContentType("image/jpg");
            putObjectResult = cosClient.putObject(bucketName, fileKey, fileInputStream, objectMetadata);
            // 关闭输入流...
        } catch (Exception serverException) {
            serverException.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            close();
        }
        return putObjectResult;
    }

    public PutObjectResult qcloudUploadInput(InputStream fileInputStream, String fileKey) {
        init();
        PutObjectResult putObjectResult = null;
        try {
            // 方法2 从输入流上传(需提前告知输入流的长度, 否则可能导致 oom)
            ObjectMetadata objectMetadata = new ObjectMetadata();
            // 设置输入流长度为20971520
            //objectMetadata.setContentLength(20971520);
            // 设置 Content type, 默认是 application/octet-stream
            //content-type, 对于本地文件上传, 默认根据本地文件的后缀进行映射，如 jpg 文件映射 为image/jpg,可以不设置
            objectMetadata.setContentType("image/jpg");
            putObjectResult = cosClient.putObject(bucketName, fileKey, fileInputStream, objectMetadata);
            // 关闭输入流...
        } catch (Exception serverException) {
            serverException.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            close();
        }
        return putObjectResult;
    }

    public ObjectMetadata qcloudDownload(String keyname, String targetFilePath) {
        init();
        ObjectMetadata downObjectMeta = null;
        try {
            // 指定要下载到的本地路径
            File downFile = new File(targetFilePath);
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, keyname);
            downObjectMeta = cosClient.getObject(getObjectRequest, downFile);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            close();
        }
        return downObjectMeta;
    }


    public COSObjectInputStream qcloudDownloadInput(String keyname) {

        init();
        COSObjectInputStream cosObjectInput = null;
        try {
            // 指定要下载到的本地路径
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, keyname);
            COSObject cosObject = cosClient.getObject(getObjectRequest);
            cosObjectInput = cosObject.getObjectContent();
        } catch (Exception exception) {
            exception.printStackTrace();

            return null;
        } finally {
            close();
        }
        return cosObjectInput;
    }



}
