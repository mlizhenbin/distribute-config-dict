package org.lzbruby.config.core;

import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 功能描述：ZK配置version注册器
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.cn
 * company：org.lzbruby
 * Date: 16/6/1 Time: 12:26
 */
@Service("configDictVersionRegister")
public class ConfigDictVersionRegisterImpl extends AbstractConfigDictZookeeperLoader implements ConfigDictVersionRegister {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigDictVersionRegisterImpl.class);

    @Override
    public void register() {
        initCuratorFramework();

        // 注册两个计数器节点,all用于全量更新, single用于单个节点更新
        registerConfigDictNode(configDictParentPath + PATH_SPLIT + configDictNodeRefreshAll, ZK_NODE_INIT_VERSION);
        registerConfigDictNode(configDictParentPath + PATH_SPLIT + configDictNodeRefreshSingle, ZK_NODE_INIT_VERSION);
    }

    @Override
    public void refresh() {
        initCuratorFramework();

        String path = configDictParentPath + PATH_SPLIT + configDictNodeRefreshAll;
        DistributedAtomicLong distributedAtomicLong = getDistributedAtomicLong(path);
        try {
            // 计数器版本,version = version + 1
            distributedAtomicLong.increment();
            AtomicValue<Long> atomicValue = distributedAtomicLong.get();
            Long version = atomicValue.postValue();
            LOGGER.info("ZooKeeper ConfigDict计数器全量更新, version+1成功!, path={}, version={}", path, version);
        } catch (Exception e) {
            LOGGER.warn("全量更新ConfigDict计数器version异常!, path={}", path, e);
            throw new RuntimeException("全量刷新配置失败!");
        }
    }

    @Override
    public void refresh(long id) {
        initCuratorFramework();

        String path = configDictParentPath + PATH_SPLIT + configDictNodeRefreshSingle;
        DistributedAtomicLong distributedAtomicLong = getDistributedAtomicLong(path);
        try {
            // 计数器版本为配置的ID
            distributedAtomicLong.forceSet(id);
            AtomicValue<Long> atomicValue = distributedAtomicLong.get();
            Long version = atomicValue.postValue();
            LOGGER.info("ZooKeeper ConfigDict计数器单节点更新, 设置version成功!,id={}, path={}, version={}", id, path, version);
        } catch (Exception e) {
            LOGGER.warn("单节点更新ConfigDict计数器version异常!, id={}, path={}", id, path, e);
            throw new RuntimeException("单节点刷新配置失败!");
        }
    }

    /**
     * 注册节点计数器
     *
     * @param configDictNodePath
     * @param version
     */
    protected void registerConfigDictNode(String configDictNodePath, long version) {
        // 将配置文件更新记录到计数器中, 计数器默认数值为系统启动时间
        DistributedAtomicLong distributedAtomicLong = getDistributedAtomicLong(configDictNodePath);
        try {
            // 重启刷新时强制版本为当前时间的时间戳
            distributedAtomicLong.forceSet(version);

            // 获取计数器version值
            AtomicValue<Long> atomicValue = distributedAtomicLong.get();
            Long nodeVersion = atomicValue.postValue();

            LOGGER.info("ZooKeeper ConfigDict注册版本号成功!, configDictNodePath={}, version={}", configDictNodePath, nodeVersion);
        } catch (Exception e) {
            LOGGER.error("注册onfigDict计数器版本号失败!, configDictNodePath={}", configDictNodePath, e);
            throw new RuntimeException("注册配置失败!");
        }
    }
}
