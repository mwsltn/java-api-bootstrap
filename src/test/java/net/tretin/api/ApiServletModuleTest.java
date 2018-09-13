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

import com.google.inject.Guice;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ApiServletModuleTest {
    private ApiServletModule module;


    @Before
    public void setUp() {
        module = ApiServletModule.builder();
    }

    @Test
    public void addPackage() {
        module.addPackage("test");
        assertEquals(1, module.getPakcages().size());
        assertEquals("test", module.getPakcages().get(0));
    }

    @Test
    public void addClass() {
        module.addClass(ApiServlet.class);
        assertEquals(1, module.getClasses().size());
        assertEquals(ApiServlet.class, module.getClasses().get(0));
    }

    @Test
    public void configure() {
        module.addClass(ApiServlet.class);
        module.addPackage("test");
        ApiServletModule.Sources sources =
                Guice.createInjector(module).getInstance(ApiServletModule.Sources.class);
        assertEquals(1, sources.getPackages().size());
        assertEquals("test", sources.getPackages().get(0));
        assertEquals(1, sources.getClasses().size());
        assertEquals(ApiServlet.class, sources.getClasses().get(0));
    }
}