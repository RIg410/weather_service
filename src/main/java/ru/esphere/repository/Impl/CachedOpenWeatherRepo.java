package ru.esphere.repository.Impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.esphere.error.LoadWeatherException;
import ru.esphere.model.GeoCoordinates;
import ru.esphere.model.weather.Weather;
import ru.esphere.repository.WeatherRepo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Repository("CachedOpenWeather")
public class CachedOpenWeatherRepo implements WeatherRepo {
    private final WeatherRepo openWeatherRepo;
    private final LoadingCache<String, Weather> cityNameCache;
    private final LoadingCache<GeoCoordinates, Weather> coordCache;

    @Autowired
    public CachedOpenWeatherRepo(@Qualifier("OpenWeather") WeatherRepo openWeatherRepo) {
        this.openWeatherRepo = openWeatherRepo;
        cityNameCache = makeNameCache();
        coordCache = makeCoordCache();
    }

    private LoadingCache<GeoCoordinates, Weather> makeCoordCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build(new CacheLoader<GeoCoordinates, Weather>() {
                    @Override
                    public Weather load(GeoCoordinates key) throws Exception {
                        return openWeatherRepo.getByGeoCoordinates(key);
                    }
                });
    }

    private LoadingCache<String, Weather> makeNameCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Weather>() {
                    @Override
                    public Weather load(String key) throws Exception {
                        return openWeatherRepo.getByCityName(key);
                    }
                });
    }

    @Override
    public Weather getByCityName(String cityName) {
        return checkErr(() -> cityNameCache.get(cityName));
    }

    @Override
    public Weather getByGeoCoordinates(GeoCoordinates geoCoordinates) {
        return checkErr(() -> coordCache.get(geoCoordinates));
    }

    private Weather checkErr(Call<Weather> call) {
        try {
            return call.apply();
        } catch (Throwable e) {
            Throwable err = e.getCause();
            if (err instanceof LoadWeatherException) {
                throw (LoadWeatherException) err;
            } else {
                throw new LoadWeatherException(e.getMessage());
            }
        }
    }

    private interface Call<T> {
        T apply() throws ExecutionException;
    }
}
