package org.lzbruby.config.core;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.lzbruby.config.domain.ConfigDict;
import org.lzbruby.config.domain.ConfigDictValidFlagType;

import java.util.List;

/**
 * 功能描述：配置字典工具类
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.cn
 * company：org.lzbruby
 * Date: 16/6/1 Time: 18:37
 */
public class ConfigDictUtil {

    /**
     * 根据数据类型,获取KEY对应的VALUE, 参数类型是String, 存在参数不可空,不建议直接调用
     * <p/>
     * <p>只匹配可见数据字典</p>
     *
     * @param configType 数据字典类型
     * @param key        数据类型KEY
     * @return VALUE
     */
    public static final String getValidValue(String configType, String key) {
        return ConfigLoaderContext.getContext().getValidValue(configType, key);
    }

    /**
     * 获取配置类型下的所有配置信息, 参数类型是String, 存在参数不可空,不建议直接调用
     *
     * @param configType
     * @return
     */
    public static final List<ConfigDict> listConfigDicts(String configType) {
        return ConfigLoaderContext.getContext().listByConfigType(configType);
    }

    /**
     * 获取配置对象, 参数类型是String, 存在参数不可空,不建议直接调用
     *
     * @param configType
     * @param key
     * @return
     */
    public static final ConfigDict getValidConfigDict(String configType, String key) {
        if (StringUtils.isBlank(configType) || StringUtils.isBlank(key)) {
            return null;
        }

        List<ConfigDict> configDicts = listConfigDicts(configType);
        if (CollectionUtils.isEmpty(configDicts)) {
            return null;
        }

        for (ConfigDict configDict : configDicts) {
            if (StringUtils.equals(configDict.getKey(), key)
                    && ConfigDictValidFlagType.getByType(configDict.getValidFlag()) == ConfigDictValidFlagType.VALID_FLAG_YES) {
                return configDict;
            }
        }

        return null;
    }
}
