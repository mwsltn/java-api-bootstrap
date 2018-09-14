package net.tretin.api.core;

import com.google.inject.AbstractModule;

public class ApiServerModule extends AbstractModule {
    private ApiConfig apiConfig;

    private ApiServerModule(ApiConfig apiConfig) {
        if (apiConfig == null) throw new IllegalArgumentException();

        this.apiConfig = apiConfig;
    }

    public static Builder builder() {
        return new Builder().setDefaultConfig();
    }

    public static ApiServerModule defaults() {
        return builder().setDefaultConfig().build();
    }

    @Override
    protected void configure() {
        if (apiConfig != null) {
            bind(ApiConfig.class).toInstance(apiConfig);
        } else {
            bind(ApiConfig.class).toInstance(new DefaultConfig());
        }
    }

    public static class Builder {
        private ApiConfig c = null;

        public Builder setDefaultConfig() {
            c = null;
            return this;
        }

        public Builder setConfig(ApiConfig config) {
            if (config == null) throw new IllegalArgumentException();
            c = config;
            return this;
        }

        public ApiServerModule build() {
            if (c == null) {
                return new ApiServerModule(new DefaultConfig());
            } else {
                return new ApiServerModule(c);
            }
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

    public static abstract class AbstractConfig implements ApiConfig {
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

    public static final class DefaultConfig extends AbstractConfig {
    }
}
