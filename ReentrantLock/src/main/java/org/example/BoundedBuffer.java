package org.example;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {
    final ReentrantLock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();  // 条件：队列未满
    final Condition notEmpty = lock.newCondition(); // 条件：队列未空

    final Object[] items = new Object[100];
    int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await(); // 如果队列满了，就在 "notFull" 条件上等待,换言之，唤醒的时候必定队列没满
            }
            items[putptr] = x;
            if (++putptr == items.length) putptr = 0;
            ++count;
            notEmpty.signal(); // 生产了一个，唤醒在 "notEmpty" 上等待的消费者
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await(); // 如果队列空了，就在 "notEmpty" 条件上等待，唤醒的时候必定队列没空
            }
            Object x = items[takeptr];
            if (++takeptr == items.length) takeptr = 0;
            --count;
            notFull.signal(); // 消费了一个，唤醒在 "notFull" 上等待的生产者
            return x;
        } finally {
            lock.unlock();
        }
    }
}