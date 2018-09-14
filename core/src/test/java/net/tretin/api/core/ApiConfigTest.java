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

import com.google.inject.Guice;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class ApiConfigTest {

    @Test
    public void instantiate() {
        ApiServerModule.ApiConfig config = Guice.createInjector(ApiServerModule.defaults())
                .getInstance(ApiServerModule.ApiConfig.class);
        assertNotNull(config);
        assertEquals(8080, config.getPort());
        config = Guice.createInjector(
                ApiServerModule.builder()
                        .setConfig(
                                new ApiServerModule.AbstractConfig() {
                                    @Override
                                    public int getPort() {
                                        return 1234;
                                    }
                                }
                        )
                        .build()
        ).getInstance(ApiServerModule.ApiConfig.class);
        assertNotNull(config);
        assertEquals(1234, config.getPort());
    }

}
