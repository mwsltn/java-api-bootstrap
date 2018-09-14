package net.tretin.api.core;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.eclipse.jetty.http.HttpCompliance;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.MonitoredQueuedThreadPool;

@Singleton
public class ApiServer {
    private volatile boolean isRunning = false;

    private final Server server;

    @Inject
    public ApiServer(ApiServerModule.ApiConfig config, ApiServlet servlet, ApiGuice apiGuice) {
        MonitoredQueuedThreadPool threadPool = new MonitoredQueuedThreadPool();
        threadPool.setMinThreads(config.getMinThreads());
        threadPool.setMaxThreads(config.getMaxThreads());
        threadPool.setName(config.getPoolName());

        Server server = new Server(new MonitoredQueuedThreadPool());

        ServerConnector http = new ServerConnector(
                server,
                config.getNumAcceptors(),
                config.getNumSelectors(),
                new HttpConnectionFactory(new HttpConfiguration(), HttpCompliance.RFC7230)
//                new HTTP2ServerConnectionFactory(new HttpConfiguration()),
//                new ALPNServerConnectionFactory()
        );

        http.setPort(config.getPort());
        http.setHost(config.getHost());
        http.setIdleTimeout(config.getIdleTimeout());
        http.setSoLingerTime(config.getSoLingerTime());

        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        context.addServlet(new ServletHolder(servlet), "/*");

        server.setHandler(context);

        this.server = server;

        final ApiServer _this = this;
        apiGuice.addModules(
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(Server.class).toInstance(_this.server);
                    }
                }
        );
    }

    public void start() throws ApiException {
        try {
            this.server.start();
        } catch (Exception e) {
            throw new ApiException("could not start Jetty", e);
        }

        this.isRunning = true;

        while (this.isRunning) {
            try {
                server.join();
            } catch (InterruptedException e) {
                //noinspection StatementWithEmptyBody
                if (this.isRunning) {
                    //TODO: log suprioius exceptions
                    //log this event, perhaps report back as metric, but we sorta want this thread to always hang until
                    //we explicitly call stop().
                } else {
                    throw new ApiException("didn't expect interrupt while not running");
                }
            }
        }
    }

    public void stop() throws ApiException {
        this.isRunning = false;
        try {
            server.stop();
        } catch (Exception e) {
            throw new ApiException("can't stop server, e)");
        }
    }

}
