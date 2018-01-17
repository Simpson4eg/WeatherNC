package com.omelchenko.model;

public class WeatherSource {

    private final String sourceName;

    //Request parts
    private final String requestURL;
    private final String apiKey;
    private final String locationToken;

    //tokens to find in response
    private final String temperatureInResponse;
    private final String windSpeedInResponse;
    private final String windDegreeInResponse;
    private final String humidityInResponse;
    private final String pressureInResponse;

    public WeatherSource(
            String sourceName,
            String requestURL,
            String apiKey,
            String locationToken,
            String temperatureInResponse,
            String windSpeedInResponse,
            String windDegreeInResponse,
            String humidityInResponse,
            String pressureInResponse) {
        this.requestURL = requestURL;
        this.sourceName = sourceName;
        this.apiKey = apiKey;
        this.locationToken = locationToken;
        this.temperatureInResponse = temperatureInResponse;
        this.windSpeedInResponse = windSpeedInResponse;
        this.windDegreeInResponse = windDegreeInResponse;
        this.humidityInResponse = humidityInResponse;
        this.pressureInResponse = pressureInResponse;
    }

    public String getLocationToken() {
        return locationToken;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public String getTemperatureInResponse() {
        return temperatureInResponse;
    }

    public String getWindSpeedInResponse() {
        return windSpeedInResponse;
    }

    public String getWindDegreeInResponse() {
        return windDegreeInResponse;
    }

    public String getHumidityInResponse() {
        return humidityInResponse;
    }

    public String getPressureInResponse() {
        return pressureInResponse;
    }

    @Override
    public String toString() {
        return "WeatherSource{" +
                "sourceName='" + sourceName + '\'' +
                ", requestURL='" + requestURL + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", locationToken='" + locationToken + '\'' +
                ", temperatureInResponse='" + temperatureInResponse + '\'' +
                ", windSpeedInResponse='" + windSpeedInResponse + '\'' +
                ", windDegreeInResponse='" + windDegreeInResponse + '\'' +
                ", humidityInResponse='" + humidityInResponse + '\'' +
                ", pressureInResponse='" + pressureInResponse + '\'' +
                '}';
    }
}
