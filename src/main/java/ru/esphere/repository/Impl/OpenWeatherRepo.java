package ru.esphere.repository.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.esphere.error.LoadWeatherException;
import ru.esphere.model.GeoCoordinates;
import ru.esphere.model.weather.Weather;
import ru.esphere.repository.WeatherRepo;

@Repository("OpenWeather")
public class OpenWeatherRepo implements WeatherRepo {
    private final String appid;
    private final ObjectMapper objectMapper;

    @Autowired
    public OpenWeatherRepo(@Value("${appid}") String appid, ObjectMapper objectMapper) {
        this.appid = appid;
        this.objectMapper = objectMapper;
    }

    @Override
    public Weather getByCityName(String cityName) {
        try {
            final URIBuilder builder = new URIBuilder("http://api.openweathermap.org/data/2.5/weather")
                    .addParameter("q", cityName)
                    .addParameter("units", "metric")
                    .addParameter("appid", appid);

            final HttpResponse resp = Request.Get(builder.build()).execute().returnResponse();
            if (resp.getStatusLine().getStatusCode() == 200) {
                return objectMapper.readValue(resp.getEntity().getContent(), Weather.class);
            } else {
                throw new LoadWeatherException("Failed to load weather data. Status line: [" + resp.getStatusLine() + "]");
            }
        } catch (Exception e) {
            throw new LoadWeatherException(e.getMessage());
        }
    }

    @Override
    public Weather getByGeoCoordinates(GeoCoordinates coord) {
        try {
            final URIBuilder builder = new URIBuilder("http://api.openweathermap.org/data/2.5/weather")
                    .addParameter("lat", "" + coord.getLat())
                    .addParameter("lon", "" + coord.getLon())
                    .addParameter("appid", appid);

            final HttpResponse resp = Request.Get(builder.build()).execute().returnResponse();
            if (resp.getStatusLine().getStatusCode() == 200) {
                return objectMapper.readValue(resp.getEntity().getContent(), Weather.class);
            } else {
                throw new LoadWeatherException("Failed to load weather data. Status line: [" + resp.getStatusLine() + "]");
            }
        } catch (Exception e) {
            throw new LoadWeatherException(e.getMessage());
        }
    }
}
