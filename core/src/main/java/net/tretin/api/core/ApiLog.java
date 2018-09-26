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

import com.google.inject.Inject;
import com.google.inject.Provider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;


public final class ApiLog {
    private final static ConcurrentHashMap<Class<?>, Method> isEmptyCache = new ConcurrentHashMap<>();
    @Inject
    private Provider<MessageBuilder> _messageBuilder;

    public static BooleanSupplier isNull(Object... objects) {
        return () -> {
            for (Object o : objects) {
                if (o == null) return true;
            }
            return false;
        };
    }

    public static BooleanSupplier isEmpty(Object... objects) {
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

    public MessageBuilder ifAny(BooleanSupplier... checks) {
        for (BooleanSupplier check : checks) {
            if (check.get()) {
                return _messageBuilder.get();
            }
        }
        return new NoOpMessageBuilder();
    }

    public enum Level {
        INFO, WARN, ERROR, FATAL
    }

    public interface MessageBuilder {
        <T> MessageBuilder with(String key, T value);

        MessageBuilder message(String message);

        <T extends Throwable> void atAndThrow(Level level, Supplier<T> t);

        void at(Level level);
    }

    public interface BooleanSupplier extends Supplier<Boolean> {
    }

    private static final class NoOpMessageBuilder implements MessageBuilder {
        @Override
        public <T> MessageBuilder with(String key, T value) {
            // no-op
            return this;
        }

        @Override
        public MessageBuilder message(String message) {
            // no-op
            return this;
        }

        @Override
        public <T extends Throwable> void atAndThrow(Level level, Supplier<T> t) {
            // no-op
        }

        @Override
        public void at(Level level) {
            // no-op
        }
    }

    public static class Exmaple {
        @Inject
        private ApiLog log;

        public void foo(String s1, String s2, List<Integer> l1, Map<String, String> m1) {
            log.ifAny(isNull(s1, s2, l1, m1), isEmpty(s1, s2, l1, m1))
                    .atAndThrow(Level.INFO, IllegalArgumentException::new);
        }
    }

}
