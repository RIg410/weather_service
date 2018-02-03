package ru.esphere.repository;

import ru.esphere.model.GeoCoordinates;
import ru.esphere.model.weather.Weather;

public interface WeatherRepo {
    Weather getByCityName(String cityName);
    Weather getByGeoCoordinates(GeoCoordinates geoCoordinates);
}
