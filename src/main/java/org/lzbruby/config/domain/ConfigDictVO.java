package org.lzbruby.config.domain;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.lzbruby.config.common.NoNullStyle;

import java.io.Serializable;

/**
 * 功能描述: ConfigDictVO查询VO
 *
 * @author: lizhenbin
 * email: lizhenbin08@sina.cn
 * company: org.lzbruby
 * Date: 2016/06/02 Time: 10:14:44
 */
public class ConfigDictVO extends PagingVO implements Serializable {
    private static final long serialVersionUID = -7183496634217752204L;

    /**
     * 主键ID，自增
     */
    private Long id;

    /**
     * 配置类型
     */
    @NotBlank
    private String configType;

    /**
     * 类型模糊查询字段
     */
    private String configTypeLike;

    /**
     * 配置KEY
     */
    @NotBlank
    private String key;

    /**
     * KEY模糊查询字段
     */
    private String keyLike;

    /**
     * 配置value
     */
    @NotBlank
    private String value;

    /**
     * 值模糊查询字段
     */
    private String valueLike;

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

    public ConfigDictVO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getConfigType() {
        return configType;
    }

    public ConfigDictVO setConfigType(String configType) {
        this.configType = configType;
        return this;
    }

    public String getKey() {
        return key;
    }

    public ConfigDictVO setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public ConfigDictVO setValue(String value) {
        this.value = value;
        return this;
    }

    public String getConfigDesc() {
        return configDesc;
    }

    public ConfigDictVO setConfigDesc(String configDesc) {
        this.configDesc = configDesc;
        return this;
    }

    public String getValidFlag() {
        return validFlag;
    }

    public ConfigDictVO setValidFlag(String validFlag) {
        this.validFlag = validFlag;
        return this;
    }

    public Long getOrderId() {
        return orderId;
    }

    public ConfigDictVO setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getConfigTypeLike() {
        return configTypeLike;
    }

    public ConfigDictVO setConfigTypeLike(String configTypeLike) {
        this.configTypeLike = configTypeLike;
        return this;
    }

    public String getKeyLike() {
        return keyLike;
    }

    public ConfigDictVO setKeyLike(String keyLike) {
        this.keyLike = keyLike;
        return this;
    }

    public String getValueLike() {
        return valueLike;
    }

    public ConfigDictVO setValueLike(String valueLike) {
        this.valueLike = valueLike;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new NoNullStyle());
    }
}
