package com.omelchenko;

import com.omelchenko.model.WeatherSource;
import com.omelchenko.service.db.DBService;
import com.omelchenko.service.db.impl.DBServiceImpl;
import com.omelchenko.service.rest.impl.RestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.ArrayList;

public class WeatherUpdater {

    @Autowired
    RestServiceImpl restService;

    public WeatherUpdater(int interval) {
        run(interval);
    }

    public void run(int interval){

        restService = new RestServiceImpl();
        DBService dbService = new DBServiceImpl();
        ArrayList<WeatherSource> sourcesList = dbService.initSources();

        while(true) {
            try {
                restService.getAndUpdateActualWeatherData(sourcesList);
                System.out.println("\nData updated!\nNext update in: " + interval + " seconds!");
                Thread.sleep(interval * 1000);
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
