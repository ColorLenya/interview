package org.example;

/**
 * Hello world!
 *
 */
public class SynchronizedExample
{
    // 1. 同步实例方法，锁是当前实例 (this)
    public synchronized void instanceMethod() {
        // 同步代码
    }

    // 2. 同步静态方法，锁是当前类的Class对象 (SynchronizedExample.class)
    public static synchronized void staticMethod() {
        // 同步代码
    }

    private final Object lockObject = new Object();

    public void codeBlockMethod() {
        // 3. 同步代码块，锁是自定义的 lockObject 对象
        synchronized(lockObject) {
            // 同步代码
        }
    }
}
