package org.lzbruby.config.core;

import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Value;

/**
 * 功能描述：配置字典ZK加载器
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.cn
 * company：org.lzbruby
 * Date: 16/6/1 Time: 18:37
 */
public abstract class AbstractConfigDictZookeeperLoader {

    /**
     * zookeeper地址
     */
    @Value("${dubbo.zk.address}")
    protected String zookeeperAddress = null;

    /**
     * zookeeper version path
     */
    @Value("${zookeeper.config.dict.path}")
    protected String configDictParentPath = null;

    /**
     * zookeeper客户端
     */
    protected CuratorFramework client = null;

    /**
     * 刷新全部内存node
     */
    protected static String configDictNodeRefreshAll = "refresh_all";

    /**
     * 刷新单个节点node
     */
    protected static String configDictNodeRefreshSingle = "refresh_single";

    /**
     * 路径分隔符
     */
    protected static String PATH_SPLIT = "/";

    /**
     * initial amount of time to wait between retries
     */
    protected static final int baseSleepTimeMs = 1000;

    /**
     * max number of times to retry
     */
    protected static final int maxRetries = 3;

    /**
     * session timeout
     */
    protected static final int sessionTimeoutMs = 15000;

    /**
     * 节点初始化版本
     */
    protected static final long ZK_NODE_INIT_VERSION = 0l;

    /**
     * 初始化CuratorFramework
     */
    protected void initCuratorFramework() {
        if (StringUtils.isBlank(zookeeperAddress)) {
            throw new RuntimeException("配置ConfigDict, ZooKeeper地址为空!");
        }
        if (StringUtils.isBlank(configDictParentPath)) {
            throw new RuntimeException("配置ConfigDict, ZooKeeper Path为空!");
        }

        ExponentialBackoffRetry backoffRetry = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);
        client = CuratorFrameworkFactory
                .builder()
                .connectString(zookeeperAddress)
                .sessionTimeoutMs(sessionTimeoutMs)
                .retryPolicy(backoffRetry)
                .build();
        client.start();

        try {
            client.blockUntilConnected();
        } catch (InterruptedException e) {
            throw new RuntimeException("Block until a connection to ZooKeeper is available error!", e);
        }
    }

    /**
     * 连接获取配置字典计数器
     *
     * @param counterPath path to hold the value
     * @return
     */
    protected DistributedAtomicLong getDistributedAtomicLong(String counterPath) {
        if (StringUtils.isBlank(counterPath)) {
            throw new RuntimeException("配置ConfigDict, ZooKeeper counterPath为空!");
        }

        return new DistributedAtomicLong(client, counterPath, new RetryNTimes(maxRetries, baseSleepTimeMs));
    }
}
