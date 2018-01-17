package com.omelchenko.service.rest;

import com.omelchenko.model.WeatherInfoEntity;
import com.omelchenko.model.WeatherSource;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

@Service
public interface RestService {

    //Get today average weather data
    WeatherInfoEntity getLastWeather();

    //Get last weather data via request-response (from all sources)
    //and delegate it to db service
    void getAndUpdateActualWeatherData(ArrayList<WeatherSource> sourcesList) throws SQLException;

    //Get only needed values from responses
    ArrayList<WeatherInfoEntity> parseResponses(ArrayList<String> responses, ArrayList<WeatherSource> sources);

    //Get last weather data via request-response (from all sources)
    ArrayList<String> getActualWeatherData(ArrayList<WeatherSource> sourcesList);

    //Get last weather data via request-response (from single source)
    String getSingleActualWeatherData(WeatherSource source);
}
