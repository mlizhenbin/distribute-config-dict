package org.lzbruby.config.test;


import org.lzbruby.config.test.starer.JettyWebAppTestServer;
import org.lzbruby.config.test.starer.WebAppTestServer;

/**
 * 功能描述：jetty测试启动器
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.com
 * company：lzbruby
 * Date: 15/8/24 Time: 16:15
 */
public class JettyWebAppTestStarter {

    public static void main(String[] args) {
        WebAppTestServer server = new JettyWebAppTestServer();
        server.start();
    }
}
