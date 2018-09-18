package net.tretin.api.core;

import com.google.inject.AbstractModule;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public final class ApiServletModule extends AbstractModule {
    private final Set<String> packages;
    private final Set<Class<?>> classes;

    private ApiServletModule(Set<String> packages, Set<Class<?>> classes) {
        if (packages == null) {
            throw new IllegalArgumentException();
        }
        if (classes == null) {
            throw new IllegalArgumentException();
        }
        if (packages.isEmpty() && classes.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.packages = Collections.unmodifiableSet(packages);
        this.classes = Collections.unmodifiableSet(classes);
    }

    public static final ApiServletModule.Builder builder() {
        return new ApiServletModule.Builder();
    }


    public static final class Builder {
        private Set<String> packages = new HashSet<>();
        private Set<Class<?>> classes = new HashSet<>();

        public ApiServletModule.Builder addPackage(String name) {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException();
            }
            if (!Pattern.matches("[A-Za-z0-9_\\.]+", name)) {
                throw new IllegalArgumentException();
            }
            packages.add(name);
            return this;
        }

        public ApiServletModule.Builder addClass(Class<?> clazz) {
            if (clazz == null) {
                throw new IllegalArgumentException();
            }
            classes.add(clazz);
            return this;
        }

        public ApiServletModule build() {
            return new ApiServletModule(packages, classes);
        }
    }

    static final class PackageSources extends HashSet<String> {
        PackageSources(Set<String> s) {
            super(s);
        }
    }

    static final class ClassSources extends HashSet<Class<?>> {
        ClassSources(Set<Class<?>> s) {
            super(s);
        }
    }

    @Override
    protected void configure() {
        bind(PackageSources.class).toInstance(new PackageSources(packages));
        bind(ClassSources.class).toInstance(new ClassSources(classes));
    }

}
