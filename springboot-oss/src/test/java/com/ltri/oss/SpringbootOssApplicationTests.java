package com.ltri.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.ltri.oss.config.OssConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class SpringbootOssApplicationTests {
    @Autowired
    private OssConfig ossConfig;

    @Test
    void contextLoads() {
        OSS ossClient = new OSSClientBuilder().build(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
        // 列举文件。 如果不设置KeyPrefix，则列举存储空间下所有的文件。KeyPrefix，则列举包含指定前缀的文件。
        ObjectListing objectListing = ossClient.listObjects(ossConfig.getBucketName(), "");
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        for (OSSObjectSummary s : sums) {
            String key = s.getKey();
            System.out.println(s.getBucketName());
            System.out.println(key);
        }
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    void test() {
        String url = "中文测试";
        System.out.println("");
    }

}
