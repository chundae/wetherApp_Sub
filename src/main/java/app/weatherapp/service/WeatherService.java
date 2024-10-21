package app.weatherapp.service;

import app.weatherapp.repository.RealWeatherRepository;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private RealWeatherRepository weatherRepository;

    public WeatherService(RealWeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }


}
