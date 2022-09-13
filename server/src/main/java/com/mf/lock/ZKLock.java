package com.mf.lock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Slf4j
@Component
@RequiredArgsConstructor
public class ZKLock implements Lock {

    private static final String LOCK_PATH = "/lock";

    private final ZkClient zkClient;
    @Override
    public void lock() {
        if (!tryLock()) {
            CountDownLatch countDownLatch = new CountDownLatch(1);
           IZkDataListener dataListener = new IZkDataListener(){
               @Override
               public void handleDataChange(String dataPath, Object data) throws Exception {

               }

               @Override
               public void handleDataDeleted(String dataPath) throws Exception {
                    log.info("*** 锁释放了, 可以重新获取锁 ***");
                    countDownLatch.countDown();
               }
           };

           zkClient.subscribeDataChanges(LOCK_PATH, dataListener);

            try {
                countDownLatch.await();
                zkClient.unsubscribeDataChanges(LOCK_PATH, dataListener);
                lock();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        try {
            zkClient.createEphemeral(LOCK_PATH, Thread.currentThread().getName());
        } catch (ZkNodeExistsException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        zkClient.delete(LOCK_PATH);

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
