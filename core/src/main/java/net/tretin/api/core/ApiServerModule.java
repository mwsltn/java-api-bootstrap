package net.tretin.api.core;

import com.google.inject.AbstractModule;

import java.util.Optional;

public class ApiServerModule extends AbstractModule {
    public static ApiServerModule withDeafultConfig() {
        return new ApiServerModule(new ApiConfig() {});
    }

    public static ApiServerModule withConfig(ApiConfig apiConfig) {
        if (apiConfig == null) throw new IllegalArgumentException();
        return new ApiServerModule(apiConfig);
    }

    private ApiConfig apiConfig;

    private ApiServerModule(ApiConfig apiConfig) {
        if (apiConfig == null) throw new IllegalArgumentException();
        this.apiConfig = apiConfig;
    }

    @Override
    protected void configure() {
        bind(ApiConfig.class).toInstance(apiConfig);
//        bind(ApiLog.class);
        bind(ApiLog.MessageBuilderFactory.class).toInstance(() -> new ApiLog.MessageBuilder() {
            @Override
            public <T> ApiLog.MessageBuilder with(String key, T value) {
                return null;
            }

            @Override
            public ApiLog.Loggable message(String message) {
                return new ApiLog.Loggable() {
                    @Override
                    public void at(ApiLog.Level level) {

                    }
                };
            }
        });
    }
}
