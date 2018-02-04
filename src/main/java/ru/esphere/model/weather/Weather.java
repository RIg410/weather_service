package ru.esphere.model.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    @JsonProperty("main")
    private MainData mainData;

    private Wind wind;

    @JsonProperty("name")
    private String cityName;

    public MainData getMainData() {
        return mainData;
    }

    public void setMainData(MainData mainData) {
        this.mainData = mainData;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "mainData=" + mainData +
                ", wind=" + wind +
                ", cityName='" + cityName + '\'' +
                '}';
    }
}