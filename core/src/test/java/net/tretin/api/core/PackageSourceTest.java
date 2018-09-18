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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class PackageSourceTest extends AbstractTestCase<ApiServletModule.PackageSources> {
    public PackageSourceTest() {
        super(
                ApiServletModule.PackageSources.class,
                ApiServletModule.builder()
                        .addPackage("net.tretin.api.core.testrs")
                        .build()
        );
    }

    @Override
    public void testInstantiatedObject() {
        assertNotNull(getT());
        assertEquals(1, getT().size());
        getT().forEach(s -> assertEquals("net.tretin.api.core.testrs", s));
    }
}
