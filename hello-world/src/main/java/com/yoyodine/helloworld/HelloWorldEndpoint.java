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

import javax.ws.rs.*;

@Produces("text/plain")
@Path("hello")
public class HelloWorldEndpoint {

    @QueryParam("lang")
    private String lang;

    @QueryParam("mood")
    private String mood;

    @GET
    @Path("{name:[A-Za-z0-9]+}")
    public String name(@PathParam("name") String name) {
        return String.format("Hello, %s!", name);
    }

}
