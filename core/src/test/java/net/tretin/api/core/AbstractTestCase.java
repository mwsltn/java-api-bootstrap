/*
 * This file is part of java-api-bootstrap.
 *
 * Foobar is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.tretin.api.core;

import com.google.inject.Stage;
import net.tretin.api.core.testrs.TestEndpoint;
import org.junit.Test;

public abstract class AbstractTestCase<T> {

    private final Class<? extends T> clazz;
    private final Api api;
    private final T t;

    protected AbstractTestCase(Class<? extends T> clazz) {
        this(
                clazz,
                ApiServletModule.builder()
                        .addClass(TestEndpoint.class)
                        .build(),
                ApiServerModule.defaults()
        );
    }

    protected AbstractTestCase(
            Class<? extends T> clazz,
            ApiServletModule servletModule
    ) {
        this(
                clazz,
                servletModule,
                ApiServerModule.defaults()
        );
    }

    protected AbstractTestCase(
            Class<? extends T> clazz,
            ApiServerModule serverModule
    ) {
        this(
                clazz,
                ApiServletModule.builder()
                        .addClass(TestEndpoint.class)
                        .build(),
                serverModule
        );
    }

    protected AbstractTestCase(
            Class<? extends T> clazz,
            ApiServletModule servletModule,
            ApiServerModule serverModule
    ) {
        if (clazz == null) throw new IllegalArgumentException();
        if (servletModule == null) throw new IllegalArgumentException();
        if (serverModule == null) throw new IllegalArgumentException();

        this.clazz = clazz;

        this.api = new Api(
                Stage.DEVELOPMENT,
                servletModule,
                serverModule
        );

        this.t = api.injector().getInstance(clazz);
    }

    final protected Api getApi() {
        return api;
    }

    final protected T getT() {
        return t;
    }

    final protected Class<? extends T> getTestClass() {
        return clazz;
    }

    @Test
    public void testInstantiation() {
        getApi().injector().getInstance(clazz);
    }

    @Test
    abstract public void testInstantiatedObject();
}
