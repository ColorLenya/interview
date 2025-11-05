package org.example;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    private final ReentrantLock lock = new ReentrantLock();
    private int count = 0;

    /**
     * lock方法
     */
    public void increment() {
        lock.lock(); // 手动加锁
        try {
            count++;
            // 可以再次调用 lock.lock()，因为它是可重入的
            // someOtherMethodThatAlsoNeedsLock();
        } finally {
            lock.unlock(); // 必须在 finally 中手动解锁
        }
    }
    public void tryLockDemo() {
        if (lock.tryLock()) { // 尝试获取锁，拿不到就算了
            try {
                // 成功获取锁，操作共享资源
                System.out.println("获取锁成功！");
            } finally {
                lock.unlock();
            }
        } else {
            // 没拿到锁，做其他事情
            System.out.println("获取锁失败，执行备选方案。");
        }
    }

    /**
     * tryLock方法
     * @throws InterruptedException
     */
    public void tryLockWithTimeoutDemo() throws InterruptedException {
        // 尝试在 1 秒内获取锁
        if (lock.tryLock(1, TimeUnit.SECONDS)) {
            try {
                // 成功获取锁
            } finally {
                lock.unlock();
            }
        } else {
            // 超时后仍未获取锁
            System.out.println("获取锁超时！");
        }
    }

    public void lockInterruptiblyDemo() throws InterruptedException {
        lock.lockInterruptibly(); // 这个方法可以响应中断Thread.interrupt()
        try {
            // 操作共享资源

        } finally {
            lock.unlock();
        }
    }
}