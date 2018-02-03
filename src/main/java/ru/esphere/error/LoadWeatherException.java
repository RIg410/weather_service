package ru.esphere.error;

public class LoadWeatherException extends RuntimeException {
    public LoadWeatherException(String err) {
        super(err);
    }
}
