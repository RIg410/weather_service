package ru.esphere.repository.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(OpenWeatherRepo.class);
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
                logger.error("Failed to load weather data by cityName [{}]. Status line: [{}]", cityName, resp.getStatusLine());
                throw new LoadWeatherException("Failed to load weather data. Perhaps the location [" + cityName + "] does not exist.");
            }
        } catch (Exception e) {
            if (!(e instanceof LoadWeatherException)) {
                logger.error("Failed to load weather data. {}", e);
                throw new LoadWeatherException("Failed to load weather data. Please try again later.");
            } else {
                throw (LoadWeatherException) e;
            }
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
                logger.error("Failed to load weather data by geo coord [{}]. Status line: [{}]", coord, resp.getStatusLine());
                throw new LoadWeatherException("Failed to load weather data. " +
                        "Perhaps the location [ lat: " + coord.getLat() + ", lon: " + coord.getLon() + "] does not exist.");
            }
        } catch (Exception e) {
            throw new LoadWeatherException(e.getMessage());
        }
    }
}
