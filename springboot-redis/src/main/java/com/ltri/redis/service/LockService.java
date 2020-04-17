package com.ltri.redis.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class LockService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    private static final String LOCK_KEY = "lockKey";

    /**
     * 问题：高并发下会超卖
     */
    public String lock1() {
        Integer redisStock = Optional.ofNullable((Integer) redisTemplate.opsForValue().get("stock")).orElse(0);
        if (redisStock <= 0) {
            System.out.println("库存不足");
            return "库存不足";
        }

        try {
            //模拟业务
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //库存减少
        Long realStock = redisTemplate.opsForValue().decrement("stock");
        System.out.println("销售成功，当前库存剩余" + realStock);
        return "销售成功，当前库存剩余" + realStock;

    }

    /**
     * 实现方式：synchronized jvm锁
     * 问题：单机情况下没问题，分布式情况会超卖
     */
    public String lock2() {
        synchronized (this) {
            Integer stock = Optional.ofNullable((Integer) redisTemplate.opsForValue().get("stock")).orElse(0);
            if (stock <= 0) {
                System.out.println("库存不足");
                return "库存不足";
            }
            try {
                //模拟业务
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //库存减少
            Long realStock = redisTemplate.opsForValue().decrement("stock");
            System.out.println("销售成功，当前库存剩余" + realStock);
            return "销售成功，当前库存剩余" + realStock;
        }
    }

    /**
     * 实现方式：redis分布式锁， set key value
     * 问题：redis宕机可能会造成死锁（锁没释放）
     */
    public String lock3() {
        Object lock = redisTemplate.opsForValue().get(LOCK_KEY);
        if (lock != null) {
            return "网络繁忙，请稍后重试";
        }
        try {
            redisTemplate.opsForValue().set(LOCK_KEY, 10);
            //模拟业务
            Thread.sleep(2000);
            Integer stock = Optional.ofNullable((Integer) redisTemplate.opsForValue().get("stock")).orElse(0);
            if (stock <= 0) {
                System.out.println("库存不足");
                return "库存不足";
            }
            //库存减少
            Long realStock = redisTemplate.opsForValue().decrement("stock");
            System.out.println("销售成功，当前库存剩余" + realStock);
            return "销售成功，当前库存剩余" + realStock;
        } catch (InterruptedException e) {
            return "网络繁忙，请稍后重试";
        } finally {
            //释放锁
            redisTemplate.delete(LOCK_KEY);
        }
    }

    /**
     * 实现方式：redis分布式锁方式 set key value NX PX。底层lua脚本 10秒后释放锁
     * 问题：如果一个程序调用时间超过10s，没执行完成锁已经释放，高并发下会造成锁一直释放导致锁失效
     */
    public String lock4() {
        //唯一Id
        String clientId = UUID.randomUUID().toString();
        try {
            //10秒后自动释放锁
            Boolean lock = redisTemplate.opsForValue().setIfAbsent(LOCK_KEY, clientId, 10, TimeUnit.SECONDS);
            if (lock != null && lock) {
                //模拟业务
                Thread.sleep(2000);
                Integer stock = Optional.ofNullable((Integer) redisTemplate.opsForValue().get("stock")).orElse(0);
                if (stock <= 0) {
                    System.out.println("库存不足");
                    return "库存不足";
                }
                //库存减少
                Long realStock = redisTemplate.opsForValue().decrement("stock");
                System.out.println("销售成功，当前库存剩余" + realStock);
                return "销售成功，当前库存剩余" + realStock;
            } else {
                return "网络繁忙，请稍后重试";
            }
        } catch (InterruptedException e) {
            return "网络繁忙，请稍后重试";
        } finally {
            String lockKey = (String) Optional.ofNullable(redisTemplate.opsForValue().get(LOCK_KEY)).orElse(null);
            //判断是否是自身上的锁
            if (lockKey != null && lockKey.equals(clientId)) {
                redisTemplate.delete(LOCK_KEY);
            }
        }
    }

    /**
     * 实现方式：redis分布式锁方式，借助redisson。底层lua脚本实现原子性，内部通过调用一个定时任务 watchdog 每隔10s检查锁情况，定时更新锁时间，其余锁并发进入自旋
     * 问题：redis集群方式，主从同步时master宕机，主从切换 slave成为master后锁还没同步情况会存在锁丢失（概率小）
     */
    public String lock5() {
        RLock lock = redissonClient.getLock(LOCK_KEY);
        try {
            lock.lock();
            //加锁
            System.out.println("start lock");
            //查询库存
            Integer stock = Optional.ofNullable((Integer) redisTemplate.opsForValue().get("stock")).orElse(0);
            if (stock <= 0) {
                System.out.println("库存不足");
                return "库存不足";
            }
            //模拟业务
            Thread.sleep(100);
            //库存减少
            Long realStock = redisTemplate.opsForValue().decrement("stock");
            System.out.println("销售成功，当前库存剩余" + realStock);
            return "销售成功，当前库存剩余" + stock;
        } catch (InterruptedException e) {
            return "网络繁忙，请稍后重试";
        } finally {
            System.out.println("end lock");
            lock.unlock();
        }
    }
}
