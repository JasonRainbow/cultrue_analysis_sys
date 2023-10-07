package com.example.demo.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.druid.util.StringUtils;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DownloadFileRequest;
import com.aliyun.oss.model.GenericRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.example.demo.config.OssConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j // 生成日志变量, 用于记录日志
public class AliOssUtil {

    @Autowired
    private OssConfigProperties ossConfigProperties;
    private static OssConfigProperties configProperties;

    @PostConstruct
    public void init() {
        configProperties = this.ossConfigProperties;
    }

    private static OSS createClient() {
        // 初始化 OssClient
        return new OSSClientBuilder().build(configProperties.getEndpoint(), configProperties.getAccessKeyId(),
                configProperties.getAccessKeySecret());
    }


    /**
     * 上传文件
     * 详细文档：https://help.aliyun.com/document_detail/84778.html
     *
     * @param fileDir
     * @param file
     * @return
     */
    public static String upload(String fileDir, MultipartFile file) {
        OSS ossClient = createClient();
        String url = "";

        // 生成文件存储路径
        StringBuilder fileKey = new StringBuilder();
        if (!fileDir.endsWith("/")) {
            fileDir = fileDir.concat("/");
        }
        String originalFilename = file.getOriginalFilename();
        String filename = FilenameUtils.getBaseName(originalFilename);
        System.out.println(filename);
        int lastIndex = originalFilename.lastIndexOf(".");
        String mainName = "";
        String ext = "";
        if (lastIndex == -1) {
            mainName += originalFilename;
            ext = getExtension(file);
        } else {
            mainName = originalFilename.substring(0, lastIndex);
            ext = originalFilename.substring(lastIndex + 1);
        }
        String fileName = mainName + "-" + IdUtil.getSnowflake().nextIdStr() + "." + ext;
        fileKey.append(fileDir).append(fileName);
        log.info("filekey={}", fileKey);
        // 上传文件
        try {
            ossClient.putObject(configProperties.getBucketName(), fileKey.toString(), file.getInputStream());
            Date expiration = DateUtil.offsetMonth(new Date(), 12);
            url = ossClient.generatePresignedUrl(configProperties.getBucketName(), fileKey.toString(), expiration).toString();
        } catch (IOException e) {
            log.error("oss文件上传出错\n", e);
        } finally {
            ossClient.shutdown();
        }
        log.info("文件：" + originalFilename + "存入OSS成功。");
        return url;
    }

    /**
     * 删除文件
     * 详细文档：https://help.aliyun.com/document_detail/84842.html
     *
     * @param fileKey
     *
     */
    public static void delete(String fileKey) {
        OSS ossClient = createClient();
        try {
            GenericRequest genericRequest = new GenericRequest(configProperties.getBucketName()).withKey(fileKey);
            ossClient.deleteObject(genericRequest);
            log.info("删除文件：" + fileKey + "成功。");

        } catch (Exception e) {
            log.error("oss删除文件出错\n", e);
        } finally {
            ossClient.shutdown();
        }

    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static final String getExtension(MultipartFile file)
    {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension))
        {
            extension = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
        }
        return extension;
    }

    /**
     * 文件下载
     * @param filekey
     */
    public static void download(String filekey) {
        OSS ossClient = createClient();
        DownloadFileRequest dfr = new DownloadFileRequest(configProperties.getBucketName(), filekey);
        try {
            ossClient.downloadFile(dfr);
        } catch (Throwable throwable) {
            log.error("oss文件下载失败", throwable);
        }
    }

    public static void showBucketObjects() {
        OSS ossClient = createClient();
        // 查看Bucket中的Object。详细请参看“SDK手册 > Java-SDK > 管理文件”。
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_object.html?spm=5176.docoss/sdk/java-sdk/manage_bucket
        ObjectListing objectListing = ossClient.listObjects(configProperties.getBucketName());
        List<OSSObjectSummary> objectSummary = objectListing.getObjectSummaries();
        System.out.println("您有以下Object：");
        for (OSSObjectSummary object : objectSummary) {
            System.out.println("\t" + object.getKey());
        }
        ossClient.shutdown();
    }
}
