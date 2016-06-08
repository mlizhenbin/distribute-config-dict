package org.lzbruby.config.service.result;

/** 
 * 功能描述: ConfigDict结果Result
 * 
 * @author: lizhenbin
 * email: lizhenbin08@sina.cn
 * company: org.lzbruby
 * Date: 2016/06/02 Time: 10:14:44
 */
public class ConfigDictException extends RuntimeException {
    private static final long serialVersionUID = -6542930092399318810L;

    private ConfigDictResult configDictResult;

    public ConfigDictException(ConfigDictResult configDictResult) {
        this.configDictResult = configDictResult;
    }

    public ConfigDictException(String message, ConfigDictResult configDictResult) {
        super(message);
        this.configDictResult = configDictResult;
    }

    public ConfigDictException(String message, Throwable cause, ConfigDictResult configDictResult) {
        super(message, cause);
        this.configDictResult = configDictResult;
    }

    public ConfigDictException(Throwable cause, ConfigDictResult configDictResult) {
        super(cause);
        this.configDictResult = configDictResult;
    }

    public ConfigDictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ConfigDictResult configDictResult) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.configDictResult = configDictResult;
    }

    public ConfigDictResult getConfigDictResult() {
        return this.configDictResult;
    }

    public void setMessage(ConfigDictResult configDictResult) {
        this.configDictResult = configDictResult;
    }
}

