package net.tretin.apibs;

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
