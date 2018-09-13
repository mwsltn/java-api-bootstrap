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

package net.tretin.api;

import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;
import com.google.inject.internal.InternalInjectorCreator;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.server.spi.AbstractContainerLifecycleListener;
import org.glassfish.jersey.server.spi.Container;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;
import org.jvnet.hk2.guice.bridge.api.HK2IntoGuiceBridge;

import java.util.Arrays;
import java.util.Optional;

public class ApiGuice {

    private Injector guice;

    public ApiGuice(Stage stage, Module... modules) {
        this.guice = new InternalInjectorCreator()
                .addModules(Arrays.asList(modules))
                .stage(stage)
                .build();
    }

    AbstractContainerLifecycleListener onStartupListener() {
        return new AbstractContainerLifecycleListener() {
            @Override
            public void onStartup(Container container) {
                GuiceBridge.getGuiceBridge()
                        .initializeGuiceBridge(getServiceLocator(jerseyInjector(container)));
                getServiceLocator(jerseyInjector(container)).getService(GuiceIntoHK2Bridge.class)
                        .bridgeGuiceInjector(guice);
                guice = guice.createChildInjector(new HK2IntoGuiceBridge(getServiceLocator(jerseyInjector(container))));
            }

            ServiceLocator getServiceLocator(InjectionManager jerseyInjector) {
                return Optional.of(jerseyInjector.getInstance(ServiceLocator.class))
                        .orElseThrow(ApiRuntimeException::new);
            }

            InjectionManager jerseyInjector(Container container) {
                return container.getApplicationHandler().getInjectionManager();
            }
        };
    }


}
