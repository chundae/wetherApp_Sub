package app.weatherapp.unitTest;

import app.weatherapp.domain.Location;
import app.weatherapp.dto.RegionDTO;
import app.weatherapp.repository.LocationRepository;
import app.weatherapp.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class TestLocationService {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetLocationDTO(){
        //given
        String Lv1 = "서울특별시";
        String Lv2 = "종로구";
        String Lv3 = "혜화동";
        RegionDTO regionDTO = new RegionDTO(Lv1, Lv2, Lv3);

        Location location = new Location();
        location.setLocationId(1L); // 테스트용 ID 설정
        location.setRegionLv1(Lv1);
        location.setRegionLv2(Lv2);
        location.setRegionLv3(Lv3);
        location.setCodeX("60");
        location.setCodeY("127");

        when(locationRepository.findByRegionLv1AndRegionLv2AndRegionLv3(Lv1, Lv2, Lv3))
                .thenReturn(Optional.of(location));

        //when
        Location result = locationService.getlocation(regionDTO);

        //then
        assertNotNull(result);
        assertEquals(Lv1, result.getRegionLv1());
        assertEquals(Lv2, result.getRegionLv2());
        assertEquals(Lv3, result.getRegionLv3());
        assertEquals(location.getCodeX(), result.getCodeX());
        assertEquals(location.getCodeY(), result.getCodeY());
        System.out.println("result = " + result);
    }
}
