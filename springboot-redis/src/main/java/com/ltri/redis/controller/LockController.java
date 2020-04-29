package com.ltri.redis.controller;


import com.ltri.redis.service.LockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ltri
 * @since 2020-03-28
 */
@RestController
public class LockController {
    @Autowired
    private LockService lockService;

    @PostMapping("/lock1")
    public Object lock1() {
        return lockService.lock1();
    }

    @PostMapping("/lock2")
    public Object lock2() {
        return lockService.lock2();
    }

    @PostMapping("/lock3")
    public Object lock3() {
        return lockService.lock3();
    }

    @PostMapping("/lock4")
    public Object lock4() {
        return lockService.lock4();
    }

    @PostMapping("/lock5")
    public Object lock5() throws InterruptedException {
        return lockService.lock5();
    }
}
