package org.lzbruby.config.common.utils;

import org.apache.commons.lang.StringUtils;
import org.lzbruby.config.domain.ConfigDict;
import org.lzbruby.config.service.result.ConfigDictException;
import org.lzbruby.config.service.result.ConfigDictResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述: ConfigDict Service
 *
 * @author: lizhenbin
 * email: lizhenbin08@sina.cn
 * company: org.lzbruby
 * Date: 2016/06/02 Time: 10:14:43
 */
public class DistributeConfigDictUtils {

    /**
     * slf4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DistributeConfigDictUtils.class);

    /**
     * Don't let anyone instantiate this class.
     */
    private DistributeConfigDictUtils() {
    }

    /**
     * 校验增加ConfigDict参数
     *
     * @param configDict
     */
    public static void validAddConfigDict(ConfigDict configDict) {
        if (StringUtils.isBlank(configDict.getConfigType())) {
            LOGGER.warn("configType配置类型为空, configDict=" + configDict);
            throw new ConfigDictException(ConfigDictResult.CONFIG_DICT_CONFIG_TYPE_NULL);
        }

        if (StringUtils.isBlank(configDict.getKey())) {
            LOGGER.warn("key配置KEY为空, configDict=" + configDict);
            throw new ConfigDictException(ConfigDictResult.CONFIG_DICT_KEY_NULL);
        }

        if (StringUtils.isBlank(configDict.getValue())) {
            LOGGER.warn("value配置value为空, configDict=" + configDict);
            throw new ConfigDictException(ConfigDictResult.CONFIG_DICT_VALUE_NULL);
        }

        if (StringUtils.isBlank(configDict.getConfigDesc())) {
            LOGGER.warn("configDesc配置描述为空, configDict=" + configDict);
            throw new ConfigDictException(ConfigDictResult.CONFIG_DICT_CONFIG_DESC_NULL);
        }

        if (configDict.getOrderId() == null) {
            LOGGER.warn("orderId排序值为空, configDict=" + configDict);
            throw new ConfigDictException(ConfigDictResult.CONFIG_DICT_ORDER_ID_NULL);
        }

    }

    /**
     * 校验修改ConfigDict参数
     *
     * @param configDict
     */
    public static void validUpdateConfigDict(ConfigDict configDict) {
        if (configDict.getId() == null) {
            LOGGER.warn("id主键ID，自增为空, configDict=" + configDict);
            throw new ConfigDictException(ConfigDictResult.CONFIG_DICT_ID_NULL);
        }
    }
}
