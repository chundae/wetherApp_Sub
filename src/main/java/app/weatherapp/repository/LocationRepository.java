package app.weatherapp.repository;

import app.weatherapp.domain.Location;
import app.weatherapp.dto.RegionDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByRegionLv1AndRegionLv2AndRegionLv3(String regionLv1, String regionLv2, String regionLv3);

    Optional<Location> findByRegionLv1AndRegionLv2AndRegionLv3IsNull(String regionLv1, String regionLv2);
}
