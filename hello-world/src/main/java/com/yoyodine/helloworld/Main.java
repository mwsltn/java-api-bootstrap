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

package com.yoyodine.helloworld;

import com.google.inject.Stage;
import net.tretin.api.core.*;

public class Main {
    public static void main(String[] args) {
        System.out.printf("Hello World...");
        ApiServer server = new ApiGuice(
                Stage.DEVELOPMENT,
                ApiServletModule.builder()
                        .addClass(HelloWorldEndpoint.class)
                        .build(),
                ApiServerModule.defaults()
        ).server();

        try {
            server.start();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
