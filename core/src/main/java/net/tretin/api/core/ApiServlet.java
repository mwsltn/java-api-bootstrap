package net.tretin.api.core;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

@Singleton
public final class ApiServlet extends ServletContainer {
    @Inject
    public ApiServlet(
            ResourceConfig resourceConfig,
            ApiServletModule.PackageSources packageSources,
            ApiServletModule.ClassSources classSources,
            Api.BindingListener bindingListener) {
        super(resourceConfig);

        if (resourceConfig == null) throw new IllegalArgumentException();
        if (packageSources == null) throw new IllegalArgumentException();
        if (classSources == null) throw new IllegalArgumentException();
        if (packageSources.isEmpty() && classSources.isEmpty()) throw new IllegalArgumentException();
        if (bindingListener == null) throw new IllegalArgumentException();

        resourceConfig.packages(packageSources.toArray(new String[0]));
        resourceConfig.registerClasses(classSources.toArray(new Class<?>[0]));
        resourceConfig.register(bindingListener);
    }
}
