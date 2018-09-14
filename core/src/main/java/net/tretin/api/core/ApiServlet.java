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
            ApiServletModule.BindingListener bindingListener) {
        super(resourceConfig);
        resourceConfig.packages(packageSources.toArray(new String[0]));
        resourceConfig.registerClasses(classSources.toArray(new Class<?>[0]));
        //resourceConfig.register(new ApiServletModule.BindingListener());
        resourceConfig.register(bindingListener);
    }
}
