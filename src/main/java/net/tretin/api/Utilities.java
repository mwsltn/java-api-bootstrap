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

class Utilities {
    static void checkArgs(boolean ... bs) {
        for (boolean b : bs) {
            if (b) throw new IllegalArgumentException();
        }
    }

    static void checkState(String message, boolean ... bs) {
        checkArgs(bs == null, bs.length == 0, message == null, message.isEmpty());
        for (boolean b : bs) {
            if (b) throw new IllegalStateException(message);
        }
    }
}
