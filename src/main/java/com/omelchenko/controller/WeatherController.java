package com.omelchenko.controller;

import com.omelchenko.model.WeatherInfoEntity;
import com.omelchenko.service.rest.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @Autowired
    RestService restService;

    @RequestMapping("/avg")
    public WeatherInfoEntity greeting() {
        return restService.getLastWeather();
    }
}