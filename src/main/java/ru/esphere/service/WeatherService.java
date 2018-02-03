package ru.esphere.service;

import ru.esphere.model.AsyncTask;
import ru.esphere.model.GeoCoordinates;

public interface WeatherService {
    AsyncTask getByCityName(String cityName);
    AsyncTask getByGeoCoordinates(GeoCoordinates coord);
}
