package app.weatherapp.service;

import app.weatherapp.domain.Location;
import app.weatherapp.dto.RegionDTO;
import app.weatherapp.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location getlocation(RegionDTO regionDTO) {
        Optional<Location> location = locationRepository.findByRegionLv1AndRegionLv2AndRegionLv3(
                regionDTO.getRegionLv1(),
                regionDTO.getRegionLv2(),
                regionDTO.getRegionLv3());
        return location.orElse(null);
    }
}