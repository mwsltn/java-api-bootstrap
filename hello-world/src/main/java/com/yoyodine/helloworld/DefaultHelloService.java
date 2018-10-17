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

import net.tretin.api.core.ApiLog;
import net.tretin.api.core.HttpException;
import net.tretin.api.core.HttpStatus;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static net.tretin.api.core.LogUtils.isEmpty;
import static net.tretin.api.core.LogUtils.isNull;

public class DefaultHelloService implements HelloService {
    private interface Greeter extends Function<String, String> {}

    private static final Map<String, Greeter> greeters = new HashMap<>();
    static {
        greeters.put("default", name -> String.format("Hello, %s!", name));
        greeters.put("happy", name -> String.format("It's really great to meet you, %s!", name));
        greeters.put("sad", name -> String.format("Why...did...you...bother, %s!", name));
        greeters.put("rude", name -> String.format("Oh, what the $*!@ do you want %s?!", name));
        greeters.put("demanding", name -> String.format("What. Out with it, %s!", name));
    }

    @Inject
    private ApiLog log;

    @Override
    public String getHelloMessage(String mood, String name) {
        log.ifAny(isNull(mood, name), isEmpty(mood, name))
                .message("illegal argument(s)")
                .atAndThrow(ApiLog.Level.ERROR, IllegalArgumentException::new);

        log.ifAny(() -> !greeters.containsKey(mood))
                .with("mood", mood)
                .message("unknown mood")
                .atAndThrow(
                        ApiLog.Level.ERROR,
                        () -> new HttpException(HttpStatus.BadRequest, "mo such mood")
                );

        return greeters.get(mood).apply(name);
    }

}
