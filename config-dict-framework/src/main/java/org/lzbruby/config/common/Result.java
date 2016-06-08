package org.lzbruby.config.common;

import java.io.Serializable;

/**
 * 功能描述: ConfigDict控制器
 *
 * @author: lizhenbin
 * email: lizhenbin08@sina.cn
 * company: org.lzbruby
 * Date: 2016/06/02 Time: 11:11:53
 */
public class Result implements Serializable {

    private static final long serialVersionUID = -6888789042963799184L;

    /**
     * 返回标志 1:成功 0：失败
     */
    private String ret;

    /**
     * 返回的错误码
     */
    private String errCode;

    /**
     * 返回的错误消息
     */
    private String errMsg;

    /**
     * 返回的数据
     * <p>
     * 成功或者失败都有可能有数据
     */
    private Object data;

    public Result() {

    }

    public Result(String ret) {
        this.ret = ret;
    }

    public Result(String ret, Object data) {
        this.ret = ret;
        this.data = data;
    }

    public Result(String ret, String errCode, String errMsg) {
        this.ret = ret;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public Result(String ret, String errCode, String errMsg, Object data) {
        this(ret, errCode, errMsg);
        this.data = data;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 成功 SUCCESS = "1"
     */
    public static final String SUCCESS = "1";

    /**
     * 失败 FAIL = "0"
     */
    public static final String FAIL = "0";
}
