package ru.esphere.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.esphere.model.GeoCoordinates;
import ru.esphere.repository.WeatherRepo;
import ru.esphere.service.TaskExecutor;
import ru.esphere.model.AsyncTask;
import ru.esphere.service.WeatherService;

import java.util.concurrent.TimeUnit;

import static ru.esphere.utils.Utils.*;

@Service
public class WeatherServiceImpl implements WeatherService {
    private final TaskExecutor taskExecutor;
    private final WeatherRepo weatherRepo;

    @Autowired
    public WeatherServiceImpl(TaskExecutor taskExecutor, @Qualifier("CachedOpenWeather") WeatherRepo weatherRepo) {
        this.taskExecutor = taskExecutor;
        this.weatherRepo = weatherRepo;
    }

    @Override
    public AsyncTask getByCityName(String cityName) {
        if (isNullOrEmpty(cityName)) {
            return AsyncTask.error("City name must not be null or empty");
        }

        return taskExecutor.submit(() -> weatherRepo.getByCityName(cityName)).get(80, TimeUnit.MILLISECONDS);
    }

    @Override
    public AsyncTask getByGeoCoordinates(GeoCoordinates coord) {
        return taskExecutor.submit(() -> weatherRepo.getByGeoCoordinates(coord)).get(80, TimeUnit.MILLISECONDS);
    }
}
