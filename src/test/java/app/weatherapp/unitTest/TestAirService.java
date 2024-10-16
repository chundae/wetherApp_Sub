package app.weatherapp.unitTest;

import app.weatherapp.dto.AirDTO;
import app.weatherapp.explorer.AirExplorer;
import app.weatherapp.service.AirQualityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TestAirService {


    @InjectMocks
    private AirQualityService airService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test //테스트 데이터 확인
    public void testGetAirQualityData() throws Exception {
        //Given
        AirExplorer airExplorer = new AirExplorer();
        String regionLv1 = "인천";
        String regionLv2 = "서구";

        //when: API 수신
        String response = airExplorer.getAirQualityData(regionLv1);
        assertNotNull(response, "response should be not null");
        System.out.println("응답 데이터 : " + response);

        AirDTO result = airService.parseAirQualityData(response,regionLv2,regionLv1);

        //Then : 검증
        assertNotNull(result, "result should not be null");
        assertThat(result.getRegionLv2()).isEqualTo(regionLv2);
        System.out.println("pm10 Value = " + result.getPm10Value());
        System.out.println("pm25 Value = " + result.getPm25Value());
        System.out.println("searchTime = " + result.getSearchTime());

    }


    @Test //수신 테스트
    public void testGetrespose() throws IOException {
        //given
        AirExplorer airExplorer = new AirExplorer();
        String regionLv1 = "인천광역시";

        //when
        String response = airExplorer.getAirQualityData(regionLv1);

        //Then
        assertNotNull(response, "API 응답 성공");
        System.out.println("response: " + response);

    }

    @Test
    public void testparseAirQualityData() throws Exception {
        //when
        AirExplorer airExplorer = new AirExplorer();
        String regionLv1 = "인천";
        String regionLv2 = "서구";
        String response = airExplorer.getAirQualityData(regionLv1);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8)));

        NodeList items = document.getElementsByTagName("item");
        for(int i = 0; i < items.getLength(); i++) {
            Element element = (Element) items.item(i);

        }

    }
}