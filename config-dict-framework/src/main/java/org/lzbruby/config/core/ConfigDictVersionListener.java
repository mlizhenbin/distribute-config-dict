package org.lzbruby.config.core;

/**
 * 功能描述：配置字典版本监听器
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.cn
 * company：org.lzbruby
 * Date: 16/6/1 Time: 14:35
 */
public interface ConfigDictVersionListener {

    /**
     * 监听parent node节点
     */
    public void parentListener();

    /**
     * 监听parent node下的所有child节点
     */
    public void childListener();

}
