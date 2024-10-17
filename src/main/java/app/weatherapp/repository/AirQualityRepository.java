package app.weatherapp.repository;

import app.weatherapp.domain.Air;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface AirQualityRepository extends JpaRepository<Air, Long> {


    List<Air> findBySearchTimeBefore(Timestamp timestamp);
}
