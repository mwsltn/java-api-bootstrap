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

import java.util.function.Supplier;


public final class ApiLog {
    public enum Level {
        INFO, WARN, ERROR, FATAL
    }

    public interface MessageBuilder {
        <T> MessageBuilder with(String key, T value);
        Loggable message(String message);
    }

    public interface MessageBuilderFactory {
        MessageBuilder newMessageBuilder();
    }

    public interface BooleanSupplier extends Supplier<Boolean> {
    }

    public static abstract class Loggable {
        final public <T extends Throwable> void atAndThrow(Level level, Supplier<T> t) throws T {
            at(level);
            throw t.get();
        }

        public abstract void at(Level level);
    }

    private static final class NoOpMessageBuilder implements MessageBuilder {
        @Override
        public <T> MessageBuilder with(String key, T value) {
            // no-op
            return this;
        }

        @Override
        public Loggable message(String message) {
            // no-op
            return new Loggable() {
                @Override
                public void at(Level level) {
                }
            };
        }
    }

    @Inject
    private MessageBuilderFactory messageBuilderFactory;

    public MessageBuilder out() {
        return messageBuilderFactory.newMessageBuilder();
    }

    public MessageBuilder ifAny(BooleanSupplier... checks) {
        for (BooleanSupplier check : checks) {
            if (check.get()) {
                return messageBuilderFactory.newMessageBuilder();
            }
        }
        return new NoOpMessageBuilder();
    }

    public MessageBuilder withSourceAndLine(BooleanSupplier... checks) {
        String[] sourceAndLine = getSourceAndLine();
        return ifAny(checks)
                .with("source", sourceAndLine[0])
                .with("line", sourceAndLine[1]);
    }

    private String[] getSourceAndLine() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        if (trace.length < 3) {
            throw new AssertionError("TODO");
        }
        return new String[]{trace[2].getFileName(), String.valueOf(trace[2].getLineNumber())};
    }



//    public static class Exmaple {
//        @Inject
//        private ApiLog log;
//
//        public void foo(String s1, String s2, List<Integer> l1, Map<String, String> m1) {
//            log.ifAny(isNull(s1, s2, l1, m1), isEmpty(s1, s2, l1, m1))
//                    .message("illegal arguments to ?")
//                    .atAndThrow(Level.INFO, IllegalArgumentException::new);
//
//            // incurs a stack trace
//            log.withSourceAndLine(
//                    isNull(s1, s2, l1, m1), isEmpty(s1, s2, l1, m1)
//            ).message("illegal arguments")
//                    .atAndThrow(Level.ERROR, IllegalArgumentException::new);
//
//
//        }
//    }

}
