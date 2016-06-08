package org.lzbruby.config.test.starer.lunch;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.lzbruby.config.test.starer.WebAppTestServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 默认的本地Jetty测试服务器
 *
 * @author lizhenbin
 */
public class JettyWebAppTestServer extends WebAppLunchConfigureLoader implements WebAppTestServer {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JettyWebAppTestServer.class);


    /**
     * Jetty服务器
     */
    private Server server;

    /**
     * 启动jetty
     */
    public void start() {
        try {

            server = new Server();
            server.setStopAtShutdown(true);

            int port = Integer.parseInt(getProperties().getProperty(WEB_APP_PORT));
            ServerConnector connector = new ServerConnector(server);
            connector.setPort(port);
            connector.setReuseAddress(false);
            server.setConnectors(new Connector[]{connector});

            String contextPath = getProperties().getProperty(CONTEXT_PATH);
            String webApp = getProperties().getProperty(WEB_APP_PATH);

            WebAppContext waContext = new WebAppContext(webApp, contextPath);
            waContext.getInitParams().put("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false");
            server.setStopAtShutdown(true);
            server.setHandler(waContext);

            server.start();
            server.join();

            LOGGER.info("启动jetty web服务成功.");

        } catch (Exception e) {
            throw new RuntimeException("启动jetty web服务出现异常", e);
        }
    }
}
