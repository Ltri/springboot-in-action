package com.ltri.oss.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.ltri.oss.config.OssConfig;
import com.ltri.oss.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@Slf4j
public class OssService {
    @Autowired
    private OssConfig ossConfig;

    private static final Set<String> FILE_TYPE = Stream.of("JPEG", "PNG", "JPG").collect(Collectors.toSet());

    /**
     * 通过文件上传,返回文件URL地址
     */
    public String upload(MultipartFile file) throws Exception {
        //文件类型后缀
        String fileType = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        if (!FILE_TYPE.contains(fileType.toUpperCase())) {
            throw new BusinessException("当前暂不支持上传该文件类型");
        }
        OSS ossClient = new OSSClientBuilder().build(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
        //拼接唯一key
        String key = UUID.randomUUID().toString() + "." + fileType;
        PutObjectRequest putObjectRequest = new PutObjectRequest(ossConfig.getBucketName(), key, file.getInputStream());
        //上传
        ossClient.putObject(putObjectRequest);
        //关闭OSSClient。
        ossClient.shutdown();
        return ossConfig.getURLPrefix() + key;
    }

    /**
     * 解析上传的压缩包
     */
    public void unZip(MultipartFile file) throws IOException {
        //文件类型后缀
        String fileType = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        if (!"zip".equals(fileType.toLowerCase())) {
            throw new BusinessException("文件类型不符，目前压缩包仅支持zip格式");
        }
        ZipInputStream zis = new ZipInputStream(file.getInputStream());
        ZipEntry ze;
        while ((ze = zis.getNextEntry()) != null) {
            System.out.println(ze.getName());
        }
    }

}
