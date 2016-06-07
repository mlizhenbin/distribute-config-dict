package org.lzbruby.config.service.result;

import org.apache.commons.lang.StringUtils;

/**
 * 功能描述: ConfigDict结果Result
 *
 * @author: lizhenbin
 * email: lizhenbin08@sina.cn
 * company: org.lzbruby
 * Date: 2016/06/02 Time: 10:14:44
 */
public enum ConfigDictResult {

    /**
     * 主键ID，自增为空
     */
    CONFIG_DICT_ID_NULL(1001, "CONFIG_DICT_ID_NULL"),

    /**
     * 配置类型为空
     */
    CONFIG_DICT_CONFIG_TYPE_NULL(1002, "CONFIG_DICT_CONFIG_TYPE_NULL"),

    /**
     * 配置KEY为空
     */
    CONFIG_DICT_KEY_NULL(1003, "CONFIG_DICT_KEY_NULL"),

    /**
     * 配置value为空
     */
    CONFIG_DICT_VALUE_NULL(1004, "CONFIG_DICT_VALUE_NULL"),

    /**
     * 配置描述为空
     */
    CONFIG_DICT_CONFIG_DESC_NULL(1005, "CONFIG_DICT_CONFIG_DESC_NULL"),

    /**
     * 审核状态为空
     */
    CONFIG_DICT_VALID_FLAG_NULL(1006, "CONFIG_DICT_VALID_FLAG_NULL"),

    /**
     * 排序值为空
     */
    CONFIG_DICT_ORDER_ID_NULL(1007, "CONFIG_DICT_ORDER_ID_NULL"),

    /**
     * 配置类型和KEY已经存在
     */
    CONFIG_DICT_TYPE_AND_KEY_EXIST(1008, "CONFIG_DICT_TYPE_AND_KEY_EXIST"),

    /**
     * 成功
     */
    SUCCESS(200, "SUCCESS"),

    /** */
    SYSTEM_ERROR(500, "SYSTEM_ERROR");

    private int code;

    private String type;

    ConfigDictResult(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public static ConfigDictResult getByType(String type) {
        if (StringUtils.isBlank(type))
            return null;

        for (ConfigDictResult result : ConfigDictResult.values()) {
            if (StringUtils.equals(type, result.getType())) {
                return result;
            }
        }

        return null;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCode() {
        return this.code;
    }

    public void setType(int code) {
        this.code = code;
    }

}
