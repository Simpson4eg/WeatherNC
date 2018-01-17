package com.omelchenko.model;

import java.util.ArrayList;

public class WeatherSourcesRepository {

    /*  Here you can add your desired resources to check weather from.
    * You have to read chosen API docs for more information
    * (how to get key, URL and what every request and response tokens means)
    *
    * @param sourceName - desired name for weather resource site/API
    * @param requestURL - full request URL for current API, except apiKey and location tokens
    * @param apiKey - key, that you can get after completing certain steps (check API docs)
    * @param locationToken - coordinates or other strings, that identify chosen location (check API docs)
    * @params {
    *   temperatureInResponse,
    *   windSpeedInResponse,
    *   windDegreeInResponse,
    *   humidityInResponse,
    *   pressureInResponse} - check docs to see, which tokens in response match their values
    */
    ArrayList<WeatherSource> sourcesList;

    public ArrayList<WeatherSource> getSourcesList() {
        return sourcesList;
    }

    public void setSourcesList(ArrayList<WeatherSource> sourcesList) {
        this.sourcesList = sourcesList;
    }

    public void initAllWeatherSources(){
        ArrayList<WeatherSource> result = new ArrayList<>();

        result.add(new WeatherSource(
                "worldweatheronline.com",
                "https://api.worldweatheronline.com/premium/v1/weather.ashx?&format=json&fx=no&mca=no",
                "key=9151cf8acf5848d8a0f73630181701",
                "q=46.4761438,30.7489421",
                "temp_C",
                "windspeedKmph",
                "winddirDegree",
                "humidity",
                "pressure"
                ));

        result.add(new WeatherSource(
                "openweathermap.org",
                "http://api.openweathermap.org/data/2.5/weather?units=metric",
                "appid=da01b7ec3b5fc3eb117e1690af6e1a72",
                "lat=46.4761438&lon=30.7489421",
                "temp",
                "speed",
                "deg",
                "humidity",
                "pressure"
                ));

        setSourcesList(result);
    }
}
