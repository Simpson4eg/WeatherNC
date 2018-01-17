package com.omelchenko.service.db.impl;

import com.omelchenko.connections.OracleConn;
import com.omelchenko.model.WeatherInfoEntity;
import com.omelchenko.model.WeatherSource;
import com.omelchenko.model.WeatherSourcesRepository;
import com.omelchenko.service.db.DBService;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@Service
public class DBServiceImpl implements DBService {

    @Override
    public ArrayList<WeatherSource> initSources() {
        WeatherSourcesRepository wsr = new WeatherSourcesRepository();
        wsr.initAllWeatherSources();
        ArrayList<WeatherSource> sourceList = wsr.getSourcesList();

        try {
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            insertSources(sourceList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sourceList;
    }

    @Override
    public void insertSources(ArrayList<WeatherSource> sources) throws SQLException {

        Connection dbConnection = new OracleConn().getDBConnection();
        Statement statement = dbConnection.createStatement();

        for (WeatherSource ws : sources) {
            String str = ("INSERT INTO SOURCES " +
                    "(NAME) " +
                    "VALUES " +
                    "('" + ws.getSourceName() + "')");
            try {
                statement.execute(str);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        dbConnection.close();
    }

    @Override
    public void createTables() throws SQLException {

        Connection dbConnection = new OracleConn().getDBConnection();
        Statement statement = dbConnection.createStatement();

        ArrayList<String> statements = new ArrayList<>();
        statements.add("drop table WEATHER_VALUES CASCADE CONSTRAINTS");
        statements.add("drop table SOURCES CASCADE CONSTRAINTS");
        statements.add("drop SEQUENCE sources_seq");
        statements.add("drop SEQUENCE values_seq");
        statements.add("CREATE TABLE SOURCES (" +
                "SOURCE_ID NUMBER(20) NOT NULL ENABLE, " +
                "NAME VARCHAR2(200 BYTE), " +
                "CONSTRAINT CON_SOURCE_ID PRIMARY KEY (SOURCE_ID))");
        statements.add("CREATE TABLE WEATHER_VALUES (" +
                "VALUE_ID NUMBER(20) NOT NULL ENABLE, " +
                "PARENT_ID NUMBER(20) NOT NULL ENABLE, " +
                "TEMPERATURE VARCHAR2(20 BYTE), " +
                "SPEED VARCHAR2(20 BYTE), " +
                "DEGREE VARCHAR2(20 BYTE), " +
                "HUMIDITY VARCHAR2(20 BYTE), " +
                "PRESSURE VARCHAR2(20 BYTE), " +
                "DATE_VALUE DATE, " +
                "CONSTRAINT CON_VALUE_ID PRIMARY KEY (VALUE_ID), " +
                "CONSTRAINT CON_PARENT_ID FOREIGN KEY (PARENT_ID) REFERENCES SOURCES (SOURCE_ID))");
        statements.add("CREATE SEQUENCE sources_seq START WITH 1");
        statements.add("CREATE SEQUENCE values_seq START WITH 1");
        statements.add("CREATE OR REPLACE TRIGGER sources_inc " +
                "BEFORE INSERT ON SOURCES " +
                "FOR EACH ROW " +
                "BEGIN " +
                "SELECT sources_seq.NEXTVAL " +
                "INTO :new.SOURCE_ID " +
                "FROM dual;" +
                "END;");
        statements.add("CREATE OR REPLACE TRIGGER values_inc " +
                "BEFORE INSERT ON WEATHER_VALUES " +
                "FOR EACH ROW " +
                "BEGIN " +
                "SELECT values_seq.NEXTVAL " +
                "INTO :new.VALUE_ID " +
                "FROM dual;" +
                "END;");

        for (String st : statements) {
            try {
                statement.execute(st);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        dbConnection.close();
    }

    @Override
    public void insertActualValues(ArrayList<WeatherInfoEntity> infoEntities) throws SQLException {

        Connection dbConnection = new OracleConn().getDBConnection();
        Statement statement = dbConnection.createStatement();

        for (WeatherInfoEntity wie : infoEntities) {
            String str = ("INSERT INTO WEATHER_VALUES (" +
                    "PARENT_ID, " +
                    "TEMPERATURE, " +
                    "SPEED, " +
                    "DEGREE, " +
                    "HUMIDITY, " +
                    "PRESSURE, " +
                    "date_value)" +
                    "VALUES (" +
                    "(select source_id " +
                    "from sources " +
                    "where name = '" + wie.getName() + "'), " +
                    wie.getTemperature() + ", " +
                    wie.getWindSpeed() + ", " +
                    wie.getWindDegree() + ", " +
                    wie.getHumidity() + ", " +
                    wie.getPressure() + ", " +
                    "sysdate)");
            try {
                System.out.println(str);
                statement.execute(str);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        dbConnection.close();
    }

    @Override
    public WeatherInfoEntity selectAllSources() throws SQLException {

        Connection dbConnection = new OracleConn().getDBConnection();
        Statement statement = dbConnection.createStatement();

        String str = ("select TEMPERATURE, SPEED, DEGREE, HUMIDITY, PRESSURE " +
                "from WEATHER_VALUES " +
                "where date_value in (" +
                "select max(date_value) " +
                "from WEATHER_VALUES " +
                "group by parent_id) " +
                "order by parent_id asc");

        ResultSet rs = statement.executeQuery(str);
        int count = 0;

        int temperature = 0;
        int speed = 0;
        int degree = 0;
        int humidity = 0;
        int pressure = 0;

        while (rs.next()) {
            temperature += Integer.parseInt(rs.getString("TEMPERATURE"));
            speed += Integer.parseInt(rs.getString("SPEED"));
            degree += Integer.parseInt(rs.getString("DEGREE"));
            humidity += Integer.parseInt(rs.getString("HUMIDITY"));
            pressure += Integer.parseInt(rs.getString("PRESSURE"));
            count++;
        }

        System.out.println("Services count: " + count);
        dbConnection.close();
        return new WeatherInfoEntity(
                "Result",
                temperature / count,
                speed / count,
                degree / count,
                humidity / count,
                pressure / count);
    }
}
