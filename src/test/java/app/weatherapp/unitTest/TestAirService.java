package app.weatherapp.unitTest;

import app.weatherapp.domain.Air;
import app.weatherapp.dto.AirDTO;
import app.weatherapp.dto.RegionDTO;
import app.weatherapp.explorer.APIExplorer;
import app.weatherapp.repository.AirQualityRepository;
import app.weatherapp.service.AirQualityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestAirService {

    @Mock
    private AirQualityRepository airQualityRepository;

    @Mock
    private APIExplorer APIExplorer;

    @InjectMocks
    private AirQualityService airService; // Mock 주입된 AirService

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
//        airService = new AirQualityService(airExplorer, airQualityRepository); // AirExplorer Mock을 주입
    }




    @Test //테스트 데이터확인
    public void TestParseAirQualityData() throws Exception{
       //given(입력데이터 설정)
        String regionLv1 = "경기";
        String regionLv2 = "김포시";
        Long locationId = 1L;
        AirDTO airDTO = new AirDTO(regionLv1, regionLv2, locationId);

        //Mock XML응답설정
        String mockXml = """
                <response>
                    <item>
                    <khaiValue>79</khaiValue>
                    <so2Value>0.004</so2Value>
                    <coValue>0.4</coValue>
                    <cityName>김포시</cityName>
                    <cityNameEng>Gimpo-si</cityNameEng>
                    <pm10Value>44</pm10Value>
                    <dataTime>2024-10-17 13:00</dataTime>
                    <no2Value>0.018</no2Value>
                    <districtNumSeq>008</districtNumSeq>
                    <o3Value>0.036</o3Value>
                    <pm25Value>28</pm25Value>
                    <sidoName>경기</sidoName>
                    </item>
                </response>
                """;
        when(APIExplorer.getAirQualityData(regionLv1)).thenReturn(mockXml);

        //when(서비스 호출)
        AirDTO result = airService.parseAirQualityData(airDTO);

        //then(결과 검증)
        verify(APIExplorer).getAirQualityData(regionLv1);
        assertNotNull(result, "result should not be null");
        assertThat(result.getRegionLv1()).isEqualTo("경기");
        assertThat(result.getRegionLv2()).isEqualTo("김포시");
        assertThat(result.getPm10Value()).isEqualTo(44.0);
        assertThat(result.getPm25Value()).isEqualTo(28.0);
        assertThat(result.getSearchTime()).isEqualTo("2024-10-17 13:00");
    }


    @Test //수신 테스트
    public void testGetrespose() throws IOException {
        //given
        APIExplorer APIExplorer = new APIExplorer();
        String regionLv1 = "인천광역시";

        //when
        String response = APIExplorer.getAirQualityData(regionLv1);

        //Then
        assertNotNull(response, "API 응답 성공");
        System.out.println("response: " + response);

    }


    @Test //변환 테스트
    public void TestGetlocationData() throws Exception {
        //given (사용자입력데이터 변환 작업)
        String regionLv1 = "서울특별시";
        String regionLv2 = "종로구";

        RegionDTO regionDTO = new RegionDTO(regionLv1, regionLv2);

        //when
        AirDTO resultData = airService.convertToEntity(regionDTO);

        //then
        assertEquals("서울", resultData.getRegionLv1());
        assertEquals("종로구", resultData.getRegionLv2());
    }

    @Test //스케줄테스트
    public void TestScheduledData() throws Exception{
       //given(1시간 이전 데이터)
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(2);
        Air staleAirData = new Air();
        staleAirData.setRegionLv1("서울");
        staleAirData.setRegionLv2("종로구");
        staleAirData.setPm10Value(20.0);
        staleAirData.setPm25Value(10.0);
        staleAirData.setSearchTime(Timestamp.valueOf(oneHourAgo));

        List<Air> staleDataList = Arrays.asList(staleAirData);
        when(airQualityRepository.findBySearchTimeBefore(any(Timestamp.class))).thenReturn(staleDataList);

        //APi에서 새로운 데이터 반환되로독 MOCK설정
        String mockXmlData = """
                <response>
                    <item>
                    <khaiValue>79</khaiValue>
                    <so2Value>0.004</so2Value>
                    <coValue>0.4</coValue>
                    <cityName>종로구</cityName>
                    <cityNameEng>jongro-go</cityNameEng>
                    <pm10Value>44</pm10Value>
                    <dataTime>2024-10-17 13:00:00</dataTime>
                    <no2Value>0.018</no2Value>
                    <districtNumSeq>008</districtNumSeq>
                    <o3Value>0.036</o3Value>
                    <pm25Value>28</pm25Value>
                    <sidoName>서울</sidoName>
                    </item>
                </response>
                """;
        //XML데이터 반환하도록 설정
        when(APIExplorer.getAirQualityData("서울")).thenReturn(mockXmlData);

        //when 스케줄러 호출
        airService.updateAirQuality();

        //then 갱신데이터 확인
        assertThat(staleAirData.getPm10Value()).isEqualTo(44.0);
        assertThat(staleAirData.getPm25Value()).isEqualTo(28.0);
        assertThat(staleAirData.getSearchTime()).isEqualTo(Timestamp.valueOf("2024-10-17 13:00:00"));

        //갱신 데이터가 저장되는지 확인
        verify(airQualityRepository, times(1)).save(staleAirData);
    }
}