package app.weatherapp.controller;

import app.weatherapp.domain.Location;
import app.weatherapp.service.LocationService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/api/defaultLocation")
    public Map<String,String> getDefaultLocation(){
        Map<String, String> defaultLocation = new HashMap<>();
        defaultLocation.put("regionLv1", "서울특별시");
        defaultLocation.put("regionLv2", "종로구");
        return defaultLocation;
    }

    @GetMapping("/api/regions")
    public List<Location> getRegions(@RequestParam("area") String regionLv1) {
        List<Location> locaionsLv2 = locationService.getLocaionsLv2("서울특별시");

        for (Location location : locaionsLv2) {
            System.out.println(location);
        }

        return locaionsLv2;
    }


}
