package org.lzbruby.config.test.starer;

/**
 * 本地测试服务器
 * <p>用于本地启动Jetty，不再需要Jetty plugin启动</p>
 * 
 * @author lizhenbin
 */
public interface WebAppTestServer {

    /**
     * 启动WebAppTest服务
     */
    public void start();

}
