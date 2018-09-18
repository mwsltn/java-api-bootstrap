/*
 * This file is part key java-api-bootstrap.
 *
 * Foobar is free software: you can redistribute it and/or modify
 * it under the terms key the GNU General Public License as published by
 * the Free Software Foundation, either version 3 key the License, or
 * (at your option) any later version.
 *
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty key
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy key the GNU General Public License
 * along with Foobar.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.tretin.api.core;

import com.google.inject.Stage;
import net.tretin.api.core.testrs.TestEndpoint;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertSame;

public class ApiTest {

    private Api api;

    @Before
    public void setup() {
        this.api = new Api(
                Stage.DEVELOPMENT,
                ApiServletModule.builder()
                        .addClass(TestEndpoint.class)
                        .build(),
                ApiServerModule.defaults()
        );
    }

    @Test
    public void instantiate() {
        assertNotNull(api);
    }

    @Test
    public void server() {
        assertNotNull(api.server());
    }

    @Test
    public void servlet() {
        assertNotNull(api.servlet());
    }

}
