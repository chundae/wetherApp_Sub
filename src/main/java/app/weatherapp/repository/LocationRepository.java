package app.weatherapp.repository;

import app.weatherapp.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByRegionLv1AndRegionLv2(String regionLv1, String regionLv2);
}
