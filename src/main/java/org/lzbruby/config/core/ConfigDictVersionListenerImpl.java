package org.lzbruby.config.core;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.framework.recipes.cache.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 功能描述：ZK配置version改变监听器
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.cn
 * company：org.lzbruby
 * Date: 16/6/1 Time: 14:37
 */
@Service("configDictVersionListener")
public class ConfigDictVersionListenerImpl extends AbstractConfigDictZookeeperLoader implements ConfigDictVersionListener {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigDictVersionListenerImpl.class);


    @Override
    public void parentListener() {
        initCuratorFramework();

        // 创建节点监听
        NodeCache cache = new NodeCache(client, configDictParentPath, false);

        try {
            cache.start();
            cache.getListenable().addListener(new NodeCacheListener() {
                @Override
                public void nodeChanged() throws Exception {
                    ConfigLoaderContext.getContext().reload();
                    LOGGER.info("ZK config dict版本更新, 重新加载配置文件信息成功!");
                }
            });

            LOGGER.info("刷新配置监听器启动成功!");
        } catch (Exception e) {
            LOGGER.error("ZK config dict监听config dict异常!", e);
            throw new RuntimeException("监听刷新配置失败!");
        }
    }

    @Override
    public void childListener() {
        initCuratorFramework();

        final PathChildrenCache cache = new PathChildrenCache(client, configDictParentPath, true);
        try {
            cache.start();

            cache.getListenable().addListener(new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                    ChildData eventData = event.getData();
                    if (eventData == null) {
                        return;
                    }

                    String fullPath = eventData.getPath();
                    if (StringUtils.isBlank(fullPath) || ArrayUtils.isEmpty(eventData.getData())) {
                        return;
                    }
                    String nodeName = StringUtils.substringAfterLast(fullPath, PATH_SPLIT);

                    // 所有节点version
                    DistributedAtomicLong allAtomicLong = getDistributedAtomicLong(configDictParentPath + PATH_SPLIT + configDictNodeRefreshAll);
                    AtomicValue<Long> allAtomicValue = allAtomicLong.get();
                    Long allVersion = allAtomicValue.postValue();

                    // 获取当前子节点version
                    DistributedAtomicLong childAtomicLong = getDistributedAtomicLong(configDictParentPath + PATH_SPLIT + configDictNodeRefreshSingle);
                    AtomicValue<Long> childAtomicValue = childAtomicLong.get();
                    Long childVersion = childAtomicValue.postValue();

                    switch (event.getType()) {
                        case CHILD_UPDATED:
                            // 单一配置节点, version即是配置的ID
                            if (StringUtils.equals(nodeName, configDictNodeRefreshSingle) && childVersion != null && childVersion > ZK_NODE_INIT_VERSION) {
                                ConfigLoaderContext.getContext().reload(childVersion);
                                LOGGER.info("监听子节点修改, ConfigDict增量修改配置信息成功!, 配置ID={}, childVersion={}", childVersion, childVersion);
                            }

                            // 全量配置节点
                            if (StringUtils.equals(nodeName, configDictNodeRefreshAll) && allVersion != null && allVersion > ZK_NODE_INIT_VERSION) {
                                ConfigLoaderContext.getContext().reload();
                                LOGGER.info("监听子节点修改, ConfigDict全量配置信息成功! version={}", allVersion);
                            }
                            break;
                        default:
                            // 默认不做任何操作
                            LOGGER.debug("非监听节点操作, 不做配置更新, 直接返回!, type={}", event.getType());
                            break;
                    }

                }
            });
        } catch (Exception e) {
            LOGGER.error("监听所有子节点, ConfigDict监听config dict异常!, configDictParentPath={}", configDictParentPath, e);
            throw new RuntimeException("监听子节点刷新配置失败!");
        }
    }
}
