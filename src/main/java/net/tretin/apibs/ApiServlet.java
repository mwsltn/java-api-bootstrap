package net.tretin.apibs;

import com.google.inject.Injector;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.AbstractContainerLifecycleListener;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jvnet.hk2.guice.bridge.api.HK2IntoGuiceBridge;

import java.util.function.Function;

import static net.tretin.apibs.Utilities.checkArgs;

public final class ApiServlet extends ServletContainer {
    private final ResourceConfig resourceConfig = new ResourceConfig();

    public ApiServlet(ApiConfig apiConfig, Injector injector) {
        super(init(apiConfig, injector));
    }

    private static ResourceConfig init(ApiConfig apiConfig, Injector injector) {
        checkArgs(apiConfig == null, injector == null);

        return new ResourceConfig()
                .register(
                        new AbstractContainerLifecycleListener() {
                            @Override
                            public void onStartup(Container container) {
                                onStartupCallback(container, injector);
                            }
                        }
                );
    }

    private static void onStartupCallback(Container container, Injector injector) {
        checkArgs(container == null, injector == null);

        Function<Container, ServiceLocator> serviceLocator =
                (Container c) -> c.getApplicationHandler().getInjectionManager().getInstance(ServiceLocator.class);

        ApiInjector.init(
                container.getApplicationHandler().getInjectionManager(),
                serviceLocator.apply(container),
                injector.createChildInjector(new HK2IntoGuiceBridge(serviceLocator.apply(container)))
        );
    }

    public void scan(String... packages) {
        checkArgs(packages == null || packages.length == 0);

        resourceConfig.packages(packages);
    }

    public void register(Class<?> clazz) {
        checkArgs(clazz == null);

        resourceConfig.registerClasses(clazz);
    }


}
