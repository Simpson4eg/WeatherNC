package com.omelchenko.service.db;

import com.omelchenko.model.WeatherInfoEntity;
import com.omelchenko.model.WeatherSource;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

@Service
public interface DBService {

    //remake tables and insert current sources to db
    ArrayList<WeatherSource> initSources();

    //insert all weather sources to db
    void insertSources(ArrayList<WeatherSource> sources) throws SQLException;

    //create needed tables in db
    void createTables() throws SQLException;

    //insert last data from responses to db
    void insertActualValues(ArrayList<WeatherInfoEntity> infoEntities) throws SQLException;

    //get last weather data from db (from all sources)
    WeatherInfoEntity selectAllSources() throws SQLException;
}
