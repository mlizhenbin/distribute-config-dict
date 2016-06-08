package org.lzbruby.config.core;

/**
 * 功能描述：配置字典版本注册接口
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.cn
 * company：org.lzbruby
 * Date: 16/6/1 Time: 12:24
 */
public interface ConfigDictVersionRegister {

    /**
     * 注册ConfigDict到ZK, 记录版本信息
     */
    public void register();

    /**
     * 刷新计数器版本, 刷新version
     */
    public void refresh();

    /**
     * 刷新单个节点计数器version, 用于配置单节点更新
     *
     * @param id 配置id
     */
    public void refresh(long id);
}
