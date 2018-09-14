package net.tretin.api.core;

import com.google.inject.AbstractModule;

public class ApiServerModule extends AbstractModule {
    private final ApiConfig apiConfig;

    public ApiServerModule(ApiConfig apiConfig) {
        if (apiConfig == null) {
            throw new IllegalArgumentException();
        }
        this.apiConfig = apiConfig;
    }

    public ApiServerModule() {
        this.apiConfig = null;
    }

    @Override
    protected void configure() {
        if (apiConfig != null) {
            bind(ApiConfig.class).toInstance(apiConfig);
        }
    }

    public interface ApiConfig {
        int getMinThreads();

        int getMaxThreads();

        String getPoolName();

        int getNumAcceptors();

        int getNumSelectors();

        String getHost();

        int getIdleTimeout();

        int getPort();

        int getSoLingerTime();

        byte[] getShutdownSecret();
    }

    public abstract class AbstractConfig implements ApiConfig {
        @Override
        public int getMinThreads() {
            return 4;
        }

        @Override
        public int getMaxThreads() {
            return 512;
        }

        @Override
        public String getPoolName() {
            return "jetty-monitored-thread-pool";
        }

        @Override
        public int getNumAcceptors() {
            return -1;
        }

        @Override
        public int getNumSelectors() {
            return -1;
        }

        @Override
        public String getHost() {
            return "localhost";
        }

        @Override
        public int getIdleTimeout() {
            return 30000;
        }

        @Override
        public int getPort() {
            return 8080;
        }

        @Override
        public int getSoLingerTime() {
            return -1;
        }

        @Override
        public byte[] getShutdownSecret() {
            return String.valueOf("shutdown").getBytes();
        }
    }

    public final class DefaultConfig extends AbstractConfig {
    }
}
