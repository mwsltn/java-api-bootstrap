package net.tretin.api.core;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.Stage;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.server.spi.AbstractContainerLifecycleListener;
import org.glassfish.jersey.server.spi.Container;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;
import org.jvnet.hk2.guice.bridge.api.HK2IntoGuiceBridge;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class Api {
    static abstract class BindingListener extends AbstractContainerLifecycleListener {}

    private final ApiServerModule serverModule;
    private final ApiServletModule servletModule;
    private final List<Module> extensions;

    private Injector injector = null;

    public Api(Stage stage, ApiServletModule servletModule, ApiServerModule serverModule, Module... extensions) {
        if (stage == null) throw new IllegalArgumentException();
        if (servletModule == null) throw new IllegalArgumentException();
        if (serverModule == null) throw new IllegalArgumentException();
        if (extensions == null) throw new IllegalArgumentException();

        for (Module m : extensions) {
            if (m instanceof ApiServletModule) {
                throw new IllegalArgumentException("cannot install ApiServiceModule as an extension");
            }
            if (m instanceof ApiServerModule) {
                throw new IllegalArgumentException("cannot install ApiServerModule as an extension");
            }
        }

        this.servletModule = servletModule;
        this.serverModule = serverModule;
        this.extensions = Arrays.asList(extensions);

        this.injector = Guice.createInjector(
                stage,
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(BindingListener.class).toInstance(
                                new BindingListener() {
                                    @Override
                                    public void onStartup(Container container) {
                                        bridgeInjector(container);
                                    }
                                }
                        );
                    }
                },
                servletModule,
                serverModule
        );
    }

    public void bridgeInjector(Container container) {
        if (container == null) {
            throw new IllegalArgumentException();
        }

        List<Module> modules = new LinkedList<>();

        modules.addAll(extensions);
        modules.add(new HK2IntoGuiceBridge(getServiceLocator(jerseyInjector(container))));
        Injector bridged = this.injector.createChildInjector(modules);

        GuiceBridge.getGuiceBridge()
                .initializeGuiceBridge(getServiceLocator(jerseyInjector(container)));

        getServiceLocator(jerseyInjector(container))
                .getService(GuiceIntoHK2Bridge.class)
                .bridgeGuiceInjector(bridged);
    }

    private ServiceLocator getServiceLocator(InjectionManager jerseyInjector) {
        if (jerseyInjector == null) {
            throw new IllegalArgumentException();
        }

        ServiceLocator serviceLocator = jerseyInjector.getInstance(ServiceLocator.class);
        if (serviceLocator == null) {
            throw new RuntimeException("can't find service locator");
        }

        return serviceLocator;
    }

    private InjectionManager jerseyInjector(Container container) {
        return container.getApplicationHandler().getInjectionManager();
    }

    public ApiServer server() {
        return injector.getInstance(ApiServer.class);
    }

    public ApiServlet servlet() {
        return injector.getInstance(ApiServlet.class);
    }

    Injector injector() {
        return this.injector;
    }

}
