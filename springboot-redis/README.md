# Spring Boot整合redis
## 分布锁实现
1. lock1 不实现锁
- 问题：高并发下会超卖
2. lock2 synchronized jvm锁
- 问题：单机情况下没问题，分布式情况会超卖
3. lock3 redis分布式锁， set key value
- 问题：redis宕机可能会造成死锁（锁没释放）
4. lock4 redis分布式锁方式 set key value NX PX。底层lua脚本 10秒后释放锁
- 问题：如果一个程序调用时间超过10s，没执行完成锁已经释放，高并发下会造成锁一直释放导致锁失效
5. lock5（推荐） redis分布式锁方式，借助redisson。底层lua脚本实现原子性，内部通过调用一个定时任务 watchdog 每隔10s检查锁情况，定时更新锁时间，其余锁并发进入自旋
- 问题：redis集群方式，主从同步时master宕机，主从切换 slave成为master后锁还没同步情况会存在锁丢失（概率小）
