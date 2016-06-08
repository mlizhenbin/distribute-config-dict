package org.lzbruby.config.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * 功能描述：配置字典加载器
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.cn
 * company：org.lzbruby
 * Date: 16/6/1 Time: 12:26
 */
@Service("configLoader")
public class ConfigLoader implements InitializingBean, ApplicationContextAware {

    /**
     * 上下文
     */
    private static ApplicationContext applicationContext;

    @Autowired
    private ConfigDictVersionRegister configDictZookeeperRegister;

    @Autowired
    private ConfigDictVersionListener configDictVersionListener;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 加载上下文, 并且刷新
        ConfigLoaderContext.getContext();
        // 注册ZK version
        configDictZookeeperRegister.register();
        // 监听子节点
        configDictVersionListener.childListener();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ConfigLoader.setApplicationContext(applicationContext, null);
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static void setApplicationContext(ApplicationContext applicationContext, Object args) {
        ConfigLoader.applicationContext = applicationContext;
    }
}
