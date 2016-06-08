package org.lzbruby.config.test.starer.lunch;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * 功能描述：启动容器配置加载器
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.com
 * company：lzbruby
 * Date: 15/11/16 Time: 18:51
 */
public abstract class WebAppLunchConfigureLoader {

    /**
     * 配置集，作为类属性，可以被所有测试类共享，保证只被初始化一次
     */
    protected static Properties properties;

    public static Properties getProperties() {
        synchronized (JettyWebAppTestServer.class) {
            if (null == properties) {
                loadProperties();
            }
            return properties;
        }
    }

    /**
     * 加载配置文件配置集
     */
    protected static void loadProperties() {
        URL url = WebAppLunchConfigureLoader.class.getClassLoader().getResource(
                LOCAL_TEST_PROPERTIES_PATH);

        properties = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(url.getFile());
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("加载" + LOCAL_TEST_PROPERTIES_PATH + "配置文件出现异常", e);
        } finally {
            IOUtils.closeQuietly(input);
        }

        String testClassPath = StringUtils.substringBefore(url.getPath(), LOCAL_TEST_PROPERTIES_PATH);
        testClassPath = testClassPath + getProperties().getProperty(WEB_APP_PATH);
        getProperties().setProperty(WEB_APP_PATH, testClassPath);
    }

    /**
     * 测试配置文件地址
     */
    protected static final String LOCAL_TEST_PROPERTIES_PATH = "local-test.properties";

    /**
     * 端口号
     */
    protected static final String WEB_APP_PORT = "webapp.port";

    /**
     * web app资源路径
     */
    protected static final String WEB_APP_PATH = "web.app";

    /**
     * web上下文
     */
    protected static final String CONTEXT_PATH = "context.path";

}
