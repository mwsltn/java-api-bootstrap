package net.tretin.apibs;

public interface ApiConfig {
    int getPort();
    byte[] getShutdownSecret();
    byte[] getRestartSecret();
}
