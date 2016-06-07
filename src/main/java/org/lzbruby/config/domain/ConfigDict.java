package org.lzbruby.config.domain;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.ibatis.type.Alias;
import org.lzbruby.config.common.NoNullStyle;

import java.io.Serializable;

/**
 * 功能描述: ConfigDict Model
 *
 * @author: lizhenbin
 * email: lizhenbin08@sina.cn
 * company: org.lzbruby
 * Date: 2016/06/02 Time: 10:14:44
 */
@Alias("ConfigDict")
public class ConfigDict implements Serializable {
    private static final long serialVersionUID = -5222079719960864939L;

    /**
     * 主键ID，自增
     */
    private Long id;

    /**
     * 配置类型
     */
    private String configType;

    /**
     * 配置KEY
     */
    private String key;

    /**
     * 配置value
     */
    private String value;

    /**
     * 配置描述
     */
    private String configDesc;

    /**
     * 审核状态
     */
    private String validFlag;

    /**
     * 排序值
     */
    private Long orderId;

    public Long getId() {
        return id;
    }

    public ConfigDict setId(Long id) {
        this.id = id;
        return this;
    }

    public String getConfigType() {
        return configType;
    }

    public ConfigDict setConfigType(String configType) {
        this.configType = configType;
        return this;
    }

    public String getKey() {
        return key;
    }

    public ConfigDict setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public ConfigDict setValue(String value) {
        this.value = value;
        return this;
    }

    public String getConfigDesc() {
        return configDesc;
    }

    public ConfigDict setConfigDesc(String configDesc) {
        this.configDesc = configDesc;
        return this;
    }

    public String getValidFlag() {
        return validFlag;
    }

    public ConfigDict setValidFlag(String validFlag) {
        this.validFlag = validFlag;
        return this;
    }

    public Long getOrderId() {
        return orderId;
    }

    public ConfigDict setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new NoNullStyle());
    }
}
