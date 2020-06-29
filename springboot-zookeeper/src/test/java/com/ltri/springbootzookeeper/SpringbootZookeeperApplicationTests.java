package com.ltri.springbootzookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

@SpringBootTest
class SpringbootZookeeperApplicationTests {
    @Autowired
    private CuratorFramework curatorFramework;

    @Test
    void search() throws Exception {
        System.out.println(curatorFramework.getChildren().forPath("/"));
    }

    @Test
    void create() throws Exception {
        String s = curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/test", "uuuuu".getBytes());
        System.out.println("s = " + s);
    }

    @Test
    void getData() throws Exception {
        byte[] bytes = curatorFramework.getData().forPath("/test");
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
    }

    @Test
    void set() throws Exception {
        Stat stat = curatorFramework.setData().forPath("/test", "aaaaa".getBytes());
        System.out.println(stat);
    }

    @Test
    void delete() throws Exception {
        System.out.println(curatorFramework.delete().forPath("/test"));
    }

}
