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

import com.google.inject.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ApiServletTest {

    private ApiServlet servlet;

    @Before
    public void setUp() {
        new ApiGuice(
                Stage.DEVELOPMENT,
                ApiServletModule.builder().addPackage("net.tretin.api").build()
        ).injector().getInstance(ApiServlet.class);
    }

    @Test
    public void test() {
//        assertNotNull(servlet);
//        servlet.getConfiguration()
//                .getClasses()
//                .forEach(c -> System.out.println(c.getName()));
    }

    @After
    public void tearDown() {
    }


}