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

import com.google.inject.AbstractModule;

public class ApiServerModule extends AbstractModule {
    private final ApiConfig apiConfig;

    public ApiServerModule(ApiConfig apiConfig) {
        if (apiConfig == null) {
            throw new IllegalArgumentException();
        }
        this.apiConfig = apiConfig;
    }

    public ApiServerModule() {
        this.apiConfig = null;
    }

    @Override
    protected void configure() {
        if (apiConfig != null) {
            bind(ApiConfig.class).toInstance(apiConfig);
        }
    }

    public interface ApiConfig {
        int getMinThreads();

        int getMaxThreads();

        String getPoolName();

        int getNumAcceptors();

        int getNumSelectors();

        String getHost();

        int getIdleTimeout();

        int getPort();

        int getSoLingerTime();

        byte[] getShutdownSecret();
    }

    public abstract class AbstractConfig implements ApiConfig {
        @Override
        public int getMinThreads() {
            return 4;
        }

        @Override
        public int getMaxThreads() {
            return 512;
        }

        @Override
        public String getPoolName() {
            return "jetty-monitored-thread-pool";
        }

        @Override
        public int getNumAcceptors() {
            return -1;
        }

        @Override
        public int getNumSelectors() {
            return -1;
        }

        @Override
        public String getHost() {
            return "localhost";
        }

        @Override
        public int getIdleTimeout() {
            return 30000;
        }

        @Override
        public int getPort() {
            return 8080;
        }

        @Override
        public int getSoLingerTime() {
            return -1;
        }

        @Override
        public byte[] getShutdownSecret() {
            return String.valueOf("shutdown").getBytes();
        }
    }

    public final class DefaultConfig extends AbstractConfig {
    }
}
