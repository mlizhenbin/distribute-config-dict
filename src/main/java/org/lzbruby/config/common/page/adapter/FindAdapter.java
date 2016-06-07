package org.lzbruby.config.common.page.adapter;

import java.util.Map;

/**
 * 功能描述：用于find查询的封装
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.cn
 * company：org.lzbruby
 * Date: 15/9/9 Time: 11:13
 */
public interface FindAdapter<T> {

    public static final String ORDER_TYPE_ASC = "ASC";
    /**
     * 把传入的对象转化为Map
     * @param clazz
     * @return
     */
    public Map<String, Object> convert(T clazz);
    /**
     * 逐个设置动态参数
     * @param key
     * @param value
     */
    public FindAdapter<T> setFiled(String key, Object value);
    /**
     * 批量设置动态参数
     * @param dynamicFileds
     */
    public FindAdapter<T> setDynamicFileds(Map<String, Object> dynamicFileds);
}
