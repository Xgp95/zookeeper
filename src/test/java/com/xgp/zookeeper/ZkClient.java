package com.xgp.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;


/**
 * @author: Xugp
 * @date: 2022/7/14 11:54
 * @description:
 */
public class ZkClient {

    private static String connectStr = "172.16.0.91:2181,172.16.0.92:2181,172.16.0.93:2181";
    private static int sessionTimeout = 100000;
    private static ZooKeeper zooKeeper = null;

    @BeforeAll
    public static void init() throws Exception {
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
//                System.out.println(watchedEvent.getType() + "===>" + watchedEvent.getPath());
            }
        };
        zooKeeper = new ZooKeeper(connectStr, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("###>");
            }
        });
        System.out.println("===>" + (zooKeeper == null));
//        Thread.sleep(Integer.MAX_VALUE);
//        try {
//            List<String> zooKeeperChildren = zooKeeper.getChildren("/", true);
//            for (String zooKeeperChild : zooKeeperChildren) {
//                System.out.println(zooKeeperChild);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void create() throws Exception {
        System.out.println("===>" + zooKeeper);
//        String nodeCreate = zooKeeper.create("/sanguo", "shuguo".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        List<String> children = zooKeeper.getChildren("/", true);
        children.forEach(System.out::println);
    }
}
