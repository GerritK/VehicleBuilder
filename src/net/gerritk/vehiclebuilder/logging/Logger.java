package net.gerritk.vehiclebuilder.logging;

public interface Logger {
    void log(String namespace, String message);

    void info(String namespace, String message);

    void warn(String namespace, String message);

    void error(String namespace, String error);

    void error(String namespace, Exception exception);
}
