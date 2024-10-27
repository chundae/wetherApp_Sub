package app.weatherapp.unitTest;

import app.weatherapp.domain.Location;
import app.weatherapp.domain.RealWeather;
import app.weatherapp.dto.RealDTO;
import app.weatherapp.dto.RegionDTO;
import app.weatherapp.dto.URL;
import app.weatherapp.explorer.APIExplorer;
import app.weatherapp.repository.RealWeatherRepository;
import app.weatherapp.service.LocationService;
import app.weatherapp.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class TestRealWeather {

    @Mock
    private RealWeatherRepository realWeatherRepository;

    @Mock
    private APIExplorer apiExplorer;

    @InjectMocks
    private WeatherService weatherService;

    @InjectMocks
    private LocationService locationService;

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
        String response = api.getRealTimeWeatherData(new Location());

        //Then
        assertNotNull(response, "API Success Conn");
        System.out.println(response);

    }

    //데이터 수신 및 DTO 생성
    @Test
    public void testGetRealWeatherData() throws Exception{
        //given
        String regionLv1 = "인천광역시";
        String regionLv2 = "서구";
        RegionDTO region = new RegionDTO(regionLv1, regionLv2);
        Location mockLocation = new Location(55, 127);

        String mockXml =
                """
                <response>
                    <item>
                        <baseTime>1500</baseTime>
                        <baseDate>20241027</baseDate>
                        <category>PTY</category>
                        <nx>55</nx>
                        <ny>127</ny>
                        <obsrValue>0</obsrValue>
                    </item>
                    <item>
                        <baseDate>20241027</baseDate>
                        <baseTime>1500</baseTime>
                        <category>REH</category>
                        <nx>55</nx>
                        <ny>127</ny>
                        <obsrValue>75</obsrValue>
                    </item>
                    <item>
                        <baseDate>20241027</baseDate>
                        <baseTime>1500</baseTime>
                        <category>T1H</category>
                        <nx>55</nx>
                        <ny>127</ny>
                        <obsrValue>17.6</obsrValue>
                    </item>
                    <item>
                        <baseDate>20241027</baseDate>
                        <baseTime>1500</baseTime>
                        <category>VEC</category>
                        <nx>55</nx>
                        <ny>127</ny>
                        <obsrValue>284</obsrValue>
                    </item>
                    <item>
                        <baseDate>20241027</baseDate>
                        <baseTime>1500</baseTime>
                        <category>WSD</category>
                        <nx>55</nx>
                        <ny>127</ny>
                        <obsrValue>0.8</obsrValue>
                    </item>
                </response>
                """;
        when(apiExplorer.getRealTimeWeatherData(mockLocation)).thenReturn(mockXml);

        //when
        RealDTO result = weatherService.getCurrentWeather(mockLocation);

        //Then
        verify(apiExplorer).getRealTimeWeatherData(mockLocation);
        assertNotNull(result, "result should not be null");
        assertThat(result.getHumidity()).isEqualTo(75);
        assertThat(result.getTemperature()).isEqualTo(17.6);
        assertThat(result.getWindSpeed()).isEqualTo(284);
        assertThat(result.getRainFall()).isEqualTo(0);
        assertThat(result.getWindDirection()).isEqualTo(0.8);
        assertThat(result.getSearchTime()).isEqualTo("1500");
        System.out.println(result);
    }




}
