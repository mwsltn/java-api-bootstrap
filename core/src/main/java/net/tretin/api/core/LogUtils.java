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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class LogUtils {
    private final static ConcurrentHashMap<Class<?>, Method> isEmptyCache = new ConcurrentHashMap<>();

    public static ApiLog.BooleanSupplier isNull(Object... objects) {
        return () -> {
            for (Object o : objects) {
                if (o == null) return true;
            }
            return false;
        };
    }

    public static ApiLog.BooleanSupplier isEmpty(Object... objects) {
        for (Object o : objects) {
            if (!isEmptyCache.containsKey(o.getClass())) {
                isEmptyCache.put(o.getClass(), searchForIsEmptyMethod(o));
            }
            try {
                Object r = isEmptyCache.get(o.getClass()).invoke(o);
                if (r.getClass().equals(Boolean.class) || r.getClass().equals(boolean.class)) {
                    if ((Boolean) r) return () -> true;
                } else {
                    throw new AssertionError("TODO");
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalArgumentException("TODO", e);
            }
        }
        return () -> false;
    }

    private static Method searchForIsEmptyMethod(Object o) {
        Method isEmpty;
        try {
            isEmpty = o.getClass().getMethod("isEmpty", Void.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(
                    String.format("object of type '%s' has no 'isEmpty()' method",
                            o.getClass().getCanonicalName()
                    ),
                    e
            );
        }

        if (!isEmpty.getReturnType().equals(boolean.class) &&
                !isEmpty.getReturnType().equals(Boolean.class)) {
            throw new IllegalArgumentException(
                    String.format("object of type '%s' has 'isEmpty()' method with bad return type",
                            o.getClass().getCanonicalName()
                    )
            );
        }

        return isEmpty;
    }



}
