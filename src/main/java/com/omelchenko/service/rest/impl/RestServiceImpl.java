package com.omelchenko.service.rest.impl;

import com.omelchenko.connections.HttpURLConn;
import com.omelchenko.model.WeatherInfoEntity;
import com.omelchenko.model.WeatherSource;
import com.omelchenko.service.db.impl.DBServiceImpl;
import com.omelchenko.service.rest.RestService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RestServiceImpl implements RestService {

    @Override
    public WeatherInfoEntity getLastWeather() {
        try {
            return new DBServiceImpl().selectAllSources();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void getAndUpdateActualWeatherData(ArrayList<WeatherSource> sources) throws SQLException {

        ArrayList<String> responses = getActualWeatherData(sources);
        System.out.println("\nsources: " + sources.size());
        System.out.println("responses: " + responses.size() + "\n");

        for (String s : responses) {
            System.out.println(s);
        }

        ArrayList<WeatherInfoEntity> infoEntities = parseResponses(responses, sources);
        for (WeatherInfoEntity wie : infoEntities) {
            System.out.println(wie);
        }

        new DBServiceImpl().insertActualValues(infoEntities);
    }

    @Override
    public ArrayList<WeatherInfoEntity> parseResponses(ArrayList<String> responses, ArrayList<WeatherSource> sources) {

        ArrayList<WeatherInfoEntity> result = new ArrayList<>();
        Matcher m;

        for (WeatherSource ws : sources) {
            ArrayList<String> fields = new ArrayList<>();
            ArrayList<Pattern> patterns = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                patterns.add(Pattern.compile("\"" + ws.getTemperatureInResponse() + "\":\"?(.*?)}?\"?,"));
                patterns.add(Pattern.compile("\"" + ws.getWindSpeedInResponse() + "\":\"?(.*?)}?\"?,"));
                patterns.add(Pattern.compile("\"" + ws.getWindDegreeInResponse() + "\":\"?(.*?)}?\"?,"));
                patterns.add(Pattern.compile("\"" + ws.getHumidityInResponse() + "\":\"?(.*?)}?\"?,"));
                patterns.add(Pattern.compile("\"" + ws.getPressureInResponse() + "\":\"?(.*?)}?\"?,"));
            }

            for (Pattern pattern : patterns) {
                m = pattern.matcher(responses.get(sources.indexOf(ws)));
                while (m.find()) {
                    fields.add(m.group(1));
                }
            }

            String name = ws.getSourceName();
            int temperature = Integer.parseInt(fields.get(0));
            int windSpeed = Integer.parseInt(fields.get(1));
            int windDegree = Integer.parseInt(fields.get(2));
            int humidity = Integer.parseInt(fields.get(3));
            int pressure = Integer.parseInt(fields.get(4));

            WeatherInfoEntity wie = new WeatherInfoEntity(name, temperature, windSpeed, windDegree, humidity, pressure);
            result.add(wie);
        }

        return result;
    }

    @Override
    public ArrayList<String> getActualWeatherData(ArrayList<WeatherSource> sourcesList) {
        ArrayList<String> responsesList = new ArrayList<>();
        for (WeatherSource ws : sourcesList) {
            responsesList.add(getSingleActualWeatherData(ws));
        }
        return responsesList;
    }

    @Override
    public String getSingleActualWeatherData(WeatherSource source) {
        HttpURLConn http = new HttpURLConn();
        String fullURL =
                source.getRequestURL() + "&" + source.getApiKey() + "&" + source.getLocationToken();
        String response = null;
        try {
            response = http.sendGet(fullURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(response);

        return response;
    }
}
