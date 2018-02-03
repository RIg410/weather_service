package ru.esphere.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.esphere.utils.Utils;
import ru.esphere.model.GeoCoordinates;
import ru.esphere.service.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<?> weather(
            @RequestParam(value = "q", required = false) String cityName,
            @RequestParam(value = "lat", required = false) Double lat,
            @RequestParam(value = "lon", required = false) Double lot) {
        if (!Utils.isNullOrEmpty(cityName)) {
            return ResponseEntity.ok(weatherService.getByCityName(cityName));
        } else if (lat != null && lot != null) {
            return ResponseEntity.ok(weatherService.getByGeoCoordinates(new GeoCoordinates(lat, lot)));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
