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

public interface ApiConfig {
    default int getMinThreads() {
        return 4;
    }


    default int getMaxThreads() {
        return 512;
    }


    default String getPoolName() {
        return "jetty-monitored-thread-pool";
    }


    default int getNumAcceptors() {
        return -1;
    }


    default int getNumSelectors() {
        return -1;
    }


    default String getHost() {
        return "localhost";
    }


    default int getIdleTimeout() {
        return 30000;
    }


    default int getPort() {
        return 8080;
    }


    default int getSoLingerTime() {
        return -1;
    }


    default byte[] getShutdownSecret() {
        return String.valueOf("shutdown").getBytes();
    }
}
