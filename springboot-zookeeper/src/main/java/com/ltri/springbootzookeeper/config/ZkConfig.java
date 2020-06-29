package com.ltri.springbootzookeeper.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZkConfig {
    @Value("${zk.urls}")
    private String urls;
    @Value("${zk.timeout}")
    private Integer timeout;
    @Value("${zk.retry}")
    private Integer retry;

    @Bean
    public CuratorFramework curatorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(timeout, retry);
        CuratorFramework cf = CuratorFrameworkFactory.builder()
                .connectString(urls)
                .retryPolicy(retryPolicy)
                .build();
        cf.start();
        return cf;
    }
}
