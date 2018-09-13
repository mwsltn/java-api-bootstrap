package net.tretin.apibs;

import com.google.inject.Injector;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.internal.inject.InjectionManager;

import static net.tretin.apibs.Utilities.checkState;

public final class ApiInjector {
    private static final ApiInjector instance = new ApiInjector();

    private static ServiceLocator hk2;
    private static InjectionManager jersey;
    private static Injector guice;

    synchronized static void init(InjectionManager jersey, ServiceLocator hk2, Injector guice) {
        checkState(
                "init() already called",
                ApiInjector.hk2 != null, ApiInjector.jersey != null, ApiInjector.guice != null
        );
        ApiInjector.jersey = jersey;
        ApiInjector.hk2 = hk2;
        ApiInjector.guice = guice;
    }

    private ApiInjector() {
    }

    public static ApiInjector get() {
        if (instance == null) {
            throw new IllegalStateException();
        }
        return instance;
    }
}
