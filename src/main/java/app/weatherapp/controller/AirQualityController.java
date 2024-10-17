package app.weatherapp.controller;

import app.weatherapp.service.AirQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AirQualityController {

    @Autowired
    private AirQualityService airQualityService;
}
