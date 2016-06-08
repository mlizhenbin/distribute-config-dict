package org.lzbruby.config.domain;

/**
 * 功能描述：配置字典配置,配置类型及其配置KEY枚举
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.cn
 * company：org.lzbruby
 * Date: 16/6/4 Time: 20:54
 */
public enum ConfigDictType {

    ;

    /**
     * 配置类型
     */
    private String configType;

    /**
     * 配置KEY
     */
    private String configKey;


    ConfigDictType(String configType, String configKey) {
        this.configType = configType;
        this.configKey = configKey;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }
}