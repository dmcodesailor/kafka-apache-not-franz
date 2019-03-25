package com.rvnug.exoplanet.util;

public class Logger {

    public static Logger logger() {
        return new Logger();
    }

    private void log(String message) {
        System.out.println(message);
    }

    public void trace(String message) {
        log(message);
    }
    public void info(String message) {
        log(message);
    }
    public void debug(String message) {
        log(message);
    }
    public void error(String message) {
        log(message);
    }
    public void fatal(String message) {
        log(message);
    }
}
