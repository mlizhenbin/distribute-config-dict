package org.lzbruby.config.common;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.lzbruby.config.core.ConfigDictUtil;
import org.lzbruby.config.domain.ConfigDict;
import org.lzbruby.config.domain.ConfigDictType;

import java.util.List;

/**
 * 功能描述：数据字典帮助类
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.cn
 * company：org.lzbruby
 * Date: 16/6/4 Time: 22:49
 */
public final class ConfigDictHelper {

    /**
     * Don't let anyone instantiate this class.
     */
    private ConfigDictHelper() {
    }

    /**
     * 根据ConfigDictType获取可见数据字典
     *
     * @param configDictType 配置字典类型
     * @return
     */
    public static String getValue(ConfigDictType configDictType) {
        if (configDictType == null) {
            return null;
        }

        return ConfigDictUtil.getValidValue(configDictType.getConfigType(), configDictType.getConfigKey());
    }

    /**
     * 根据ConfigDictType获取可见数据字典
     *
     * @param configDictType 配置字典类型
     * @return
     */
    public static ConfigDict getConfigDict(ConfigDictType configDictType) {
        if (configDictType == null) {
            return null;
        }

        return ConfigDictUtil.getValidConfigDict(configDictType.getConfigType(), configDictType.getConfigKey());
    }

    /**
     * 通过配置KEY获取所有该配置下的可见配置字典
     *
     * @param configDictType
     * @return
     */
    public static List<ConfigDict> listConfigDicts(String configDictType) {
        if (StringUtils.isBlank(configDictType)) {
            return Lists.newArrayList();
        }

        return ConfigDictUtil.listConfigDicts(configDictType);
    }

}
