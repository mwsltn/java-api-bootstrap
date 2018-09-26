package net.tretin.api.core;

import com.google.inject.AbstractModule;

import java.util.Optional;

public class ApiServerModule extends AbstractModule {

    public static Builder builder() {
        return new Builder().setDefaultConfig();
    }

    public static ApiServerModule defaults() {
        return builder().setDefaultConfig().build();
    }

    public interface ApiConfig {
        default int getMinThreads() {
            return 4;
        }


        default int getMaxThreads() {
            return 512;
        }


        default String getPoolName() {
            return "jetty-monitored-thread-pool";
        }


        default int getNumAcceptors() {
            return -1;
        }


        default int getNumSelectors() {
            return -1;
        }


        default String getHost() {
            return "localhost";
        }


        default int getIdleTimeout() {
            return 30000;
        }


        default int getPort() {
            return 8080;
        }


        default int getSoLingerTime() {
            return -1;
        }


        default byte[] getShutdownSecret() {
            return String.valueOf("shutdown").getBytes();
        }
    }

    public static class Builder {
        private Optional<ApiConfig> apiConfig;

        public Builder setDefaultConfig() {
            apiConfig = Optional.empty();
            return this;
        }

        public Builder setConfig(ApiConfig apiConfig) {
            if (apiConfig == null) throw new IllegalArgumentException();
            this.apiConfig = Optional.of(apiConfig);
            return this;
        }

        public ApiServerModule build() {
            return apiConfig.map(ApiServerModule::new)
                    .orElseGet(
                            () -> new ApiServerModule(new ApiConfig() {
                            })
                    );
        }
    }

    private ApiConfig apiConfig;

    private ApiServerModule(ApiConfig apiConfig) {
        if (apiConfig == null) throw new IllegalArgumentException();
        this.apiConfig = apiConfig;
    }

    @Override
    protected void configure() {
        bind(ApiConfig.class).toInstance(apiConfig);
    }
}
