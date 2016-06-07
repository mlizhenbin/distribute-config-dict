package org.lzbruby.config.domain;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * 功能描述：配置字典校验状态枚举
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.cn
 * company：org.lzbruby
 * Date: 16/6/2 Time: 10:32
 */
public enum ConfigDictValidFlagType {

    VALID_FLAG_WAIT("2", "待审核"),

    VALID_FLAG_NO("0", "停用"),

    VALID_FLAG_YES("1", "启用");

    private String type;

    private String desc;

    ConfigDictValidFlagType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public ConfigDictValidFlagType setType(String type) {
        this.type = type;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public ConfigDictValidFlagType setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public static ConfigDictValidFlagType getByType(String typeCode) {
        if (StringUtils.isEmpty(typeCode)) {
            return null;
        }

        for (ConfigDictValidFlagType type : ConfigDictValidFlagType.values()) {
            if (type.getType().equals(typeCode)) {
                return type;
            }
        }
        return null;
    }

    public static Map<String, String> buildConfigDictValidFlagTypeMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        for (ConfigDictValidFlagType configDictValidFlagType : ConfigDictValidFlagType.values()) {
            map.put(configDictValidFlagType.getType(), configDictValidFlagType.getDesc());
        }

        return map;
    }
}