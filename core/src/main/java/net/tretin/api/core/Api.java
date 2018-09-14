package net.tretin.api.core;

import com.google.inject.*;
import com.google.inject.Module;
import com.google.inject.internal.InternalInjectorCreator;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class Api {
    private final static Object lock = new Object();

    private Injector guice;

    public Api(Stage stage, ApiServletModule servletModule, ApiServerModule serverModule, Module... moduleList) {
        if (stage == null) throw new IllegalArgumentException();
        if (servletModule == null) throw new IllegalArgumentException();
        if (serverModule == null) throw new IllegalArgumentException();

        List<Module> modules = new LinkedList<>();

        final Api _this = this;
        modules.add(
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(Api.class).toInstance(_this);
                    }
                }
        );

        modules.addAll(Arrays.asList(moduleList));

        this.guice = new InternalInjectorCreator()
                .addModules(modules)
                .stage(stage)
                .build();
    }

    public Injector injector() {
        return this.guice;
    }

    public Api addModules(Module... modules) {
        if (modules == null || modules.length == 0) {
            throw new IllegalArgumentException();
        }
        synchronized (lock) {
            guice = guice.createChildInjector(Arrays.asList(modules));
        }
        return this;
    }

    public ApiServer server() {
        return guice.getInstance(ApiServer.class);
    }


    public ApiServlet servlet() {
        return guice.getInstance(ApiServlet.class);
    }

}
