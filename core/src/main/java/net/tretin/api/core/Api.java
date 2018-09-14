package net.tretin.api.core;

import com.google.inject.*;
import com.google.inject.Module;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class Api {
    private final static Object lock = new Object();

    private Injector injector;

    public Api(Stage stage, ApiServletModule servletModule, ApiServerModule serverModule, Module... extensions) {
        if (stage == null) throw new IllegalArgumentException();
        if (servletModule == null) throw new IllegalArgumentException();
        if (serverModule == null) throw new IllegalArgumentException();

        for (Module m : extensions) {
            if (m instanceof ApiServletModule) {
                throw new IllegalArgumentException("cannot install ApiServiceModule as an extension");
            }
            if (m instanceof ApiServerModule) {
                throw new IllegalArgumentException("cannot install ApiServerModule as an extension");
            }
        }

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
        modules.add(servletModule);
        modules.add(serverModule);
        modules.addAll(Arrays.asList(extensions));

        this.injector = Guice.createInjector(stage, modules);
//        List<Module> modules = new LinkedList<>();
//
//        final Api _this = this;
//        modules.add(
//                new AbstractModule() {
//                    @Override
//                    protected void configure() {
//                        bind(Api.class).toInstance(_this);
//                    }
//                }
//        );
//
//        modules.addAll(Arrays.asList(moduleList));

//        this.injector = new InternalInjectorCreator()
//                .addModules(Arrays.asList(moduleList))
//                .stage(stage)
//                .build();
    }

    public Injector injector() {
        return this.injector;
    }

    public Api addModules(Module... modules) {
        if (modules == null || modules.length == 0) {
            throw new IllegalArgumentException();
        }
        synchronized (lock) {
            injector = injector.createChildInjector(Arrays.asList(modules));
        }
        return this;
    }

//    public ApiServer server() {
//        return injector.getInstance(ApiServer.class);
//    }


    public ApiServlet servlet() {
        return injector.getInstance(ApiServlet.class);
    }

}
