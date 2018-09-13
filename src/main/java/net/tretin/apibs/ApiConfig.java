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

package net.tretin.apibs;

public interface ApiConfig {
    int getSslPort();

    int getPort();

    class Default implements ApiConfig {
        @Override
        public int getPort() {
            return 8080;
        }

        @Override
        public int getSslPort() {
            return 8443;
        }

        @Override
        public byte[] getShutdownSecret() {
            return String.valueOf("shutdown")
                    .getBytes();
        }

        @Override
        public byte[] getRestartSecret() {
            return String.valueOf("shutdown")
                    .getBytes();
        }
    }
    byte[] getShutdownSecret();
    byte[] getRestartSecret();
}
