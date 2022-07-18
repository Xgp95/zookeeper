package com.xgp.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author: Xugp
 * @date: 2022/7/18 10:18
 * @description:
 */
public class CuratorTest {
    public static void main(String[] args) {
        InterProcessMutex lock1 = new InterProcessMutex(getCuratoFramework(), "/locks");
        InterProcessMutex lock2 = new InterProcessMutex(getCuratoFramework(), "/locks");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock1.acquire();
                    System.out.println("thread1 get lock");
                    lock1.acquire();
                    System.out.println("thread1 get lock again");
                    lock1.release();
                    System.out.println("thread1 release lock");
                    lock1.release();
                    System.out.println("thread1 release lock again");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock2.acquire();
                    System.out.println("thread2 get lock");
                    lock2.acquire();
                    System.out.println("thread2 get lock again");

                    Thread.sleep(5000);
                    lock2.release();
                    System.out.println("thread2 release lock");
                    lock2.release();
                    System.out.println("thread2 release lock again");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private static CuratorFramework getCuratoFramework() {
        ExponentialBackoffRetry policy = new ExponentialBackoffRetry(1000, 10);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("172.16.0.91:2181,172.16.0.92:2181,172.16.0.93:2181")
                .connectionTimeoutMs(2000)
                .sessionTimeoutMs(400000)
                .retryPolicy(policy).build();

        client.start();

        System.out.println("client start!");

        return client;
    }
}
