package com.omelchenko.model;

public class WeatherInfoEntity {
    private final String name;
    private final float temperature;
    private final float windSpeed;
    private final float windDegree;
    private final float humidity;
    private final float pressure;

    public WeatherInfoEntity(String name, float temperature, float windSpeed, float windDegree, float humidity, float pressure) {
        this.name = name;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public String getName() {
        return name;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public float getWindDegree() {
        return windDegree;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }

    @Override
    public String toString() {
        return "WeatherInfoEntity{" +
                "name='" + name + '\'' +
                ", temperature='" + temperature + " C'" +
                ", windSpeed='" + windSpeed + " km/h'" +
                ", windDegree='" + windDegree + "'" +
                ", humidity='" + humidity + " %'" +
                ", pressure='" + pressure + "'" +
                '}';
    }
}
