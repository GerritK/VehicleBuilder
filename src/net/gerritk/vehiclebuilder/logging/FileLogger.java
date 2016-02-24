package net.gerritk.vehiclebuilder.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class FileLogger implements Logger {
    private BufferedWriter writer;

    public FileLogger(File logFile) {
        if (logFile.exists()) {
            if (!logFile.delete()) {
                throw new RuntimeException("Could not delete existing logfile.");
            }
        }

        try {
            if (!logFile.createNewFile()) {
                throw new RuntimeException("Could not create logfile.");
            }

            writer = new BufferedWriter(new FileWriter(logFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void log(String namespace, String message) {
        writeLine("state", namespace, message);
    }

    @Override
    public void info(String namespace, String message) {
        writeLine("INFO", namespace, message);
    }

    @Override
    public void warn(String namespace, String message) {
        writeLine("WARNING", namespace, message);
    }

    @Override
    public void error(String namespace, String error) {
        writeLine("ERROR", namespace, error);
    }

    @Override
    public void error(String namespace, Exception exception) {
        error(namespace, exception.getMessage());
    }

    private void writeLine(String type, String namespace, String message) {
        Date now = new Date();
        try {
            writer.write(now.toString());
            writer.write("\t");
            writer.write(type.toUpperCase());
            writer.write("\t");
            writer.write(namespace);
            writer.write(": ");
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
