package app.weatherapp.repository;

import app.weatherapp.domain.RealWeather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RealWeatherRepository extends JpaRepository<RealWeather, Long> {
}
