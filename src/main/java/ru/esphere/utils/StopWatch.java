package ru.esphere.utils;

public class StopWatch {
    private final static ThreadLocal<Long> startTime = new ThreadLocal<>();

    public static void start() {
        startTime.set(System.currentTimeMillis());
    }

    public static long stop() {
        Long start = startTime.get();
        if (start != null) {
            startTime.remove();
            return System.currentTimeMillis() - start;
        } else {
            return -1;
        }
    }
}
