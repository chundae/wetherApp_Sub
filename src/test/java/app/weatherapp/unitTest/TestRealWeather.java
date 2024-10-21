package app.weatherapp.unitTest;

import app.weatherapp.domain.RealWeather;
import app.weatherapp.dto.RegionDTO;
import app.weatherapp.dto.URL;
import app.weatherapp.explorer.APIExplorer;
import app.weatherapp.repository.RealWeatherRepository;
import app.weatherapp.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestRealWeather {

    @Mock
    private RealWeatherRepository realWeatherRepository;

    @Mock
    private APIExplorer apiExplorer;

    @InjectMocks
    private WeatherService weatherService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test //수신 테스트
    public void testGetWeatherConn() throws IOException{
        //given
        APIExplorer api = new APIExplorer();
        RegionDTO region = new RegionDTO(55, 126);

        //when
        String response = api.getRealTimeWeatherData(region);

        //Then
        assertNotNull(response, "API Success Conn");
        System.out.println(response);

    }




}
