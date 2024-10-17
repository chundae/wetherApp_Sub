package app.weatherapp.service;

import app.weatherapp.domain.Air;
import app.weatherapp.domain.Location;
import app.weatherapp.dto.AirDTO;
import app.weatherapp.dto.RegionDTO;
import app.weatherapp.explorer.AirExplorer;
import app.weatherapp.repository.AirQualityRepository;
import app.weatherapp.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AirQualityService {

    private final AirQualityRepository airQualityRepository;

    @Autowired
    private LocationRepository locationRepository;

    private final AirExplorer airExplorer;

    public AirQualityService(AirExplorer airExplorer, AirQualityRepository airQualityRepository) {
        this.airExplorer = airExplorer;
        this.airQualityRepository = airQualityRepository;
    }


    //데이터변환 및 locationID 조회("서울특별시" -> "서울" (regionLv1))
    //location 데이터는 locationService 에서 데이터 조회
    public AirDTO convertToEntity(RegionDTO regionDTO) {
        String regionLv1 = convertToShortRegionLv1(regionDTO.getRegionLv1());
        String regionLv2 = regionDTO.getRegionLv2();

        return new AirDTO(regionLv1, regionLv2);
    }


    // 지역명 변환 메서드
    private String convertToShortRegionLv1(String regionLv1) {
        return switch (regionLv1) {
            case "서울특별시" -> "서울";
            case "충청남도" -> "충남";
            case "충청북도" -> "충북";
            case "경상남도" -> "경남";
            case "경상북도" -> "경북";
            case "전라남도" -> "전남";
            case "전라북도" -> "전북";
            case "강원특별자치도", "강원도" -> "강원";
            case "인천광역시" -> "인천";
            case "부산광역시" -> "부산";
            case "대구광역시" -> "대구";
            case "광주광역시" -> "광주";
            case "대전광역시" -> "대전";
            case "울산광역시" -> "울산";
            case "세종특별자치시" -> "세종";
            case "제주특별자치도" -> "제주";
            case "경기도" -> "경기";
            default -> regionLv1; // 변환되지 않는 경우 원본 반환
        };
    }


    //데이터 DTO(parseAirQualityData) -> Domain + DB 저장단계
    public void saveAirQuality(AirDTO airDTO) {
        Air air = new Air();
        air.setRegionLv1(airDTO.getRegionLv1());
        air.setRegionLv2(airDTO.getRegionLv2());
        air.setPm10Value(airDTO.getPm10Value());
        air.setPm25Value(airDTO.getPm25Value());
        air.setSearchTime(Timestamp.valueOf(airDTO.getSearchTime()));

        airQualityRepository.save(air);
    }

    //API통신 기능(locationId 추가예정)
    public AirDTO parseAirQualityData(AirDTO airDTO) throws Exception {
        String xmlData = airExplorer.getAirQualityData(airDTO.getRegionLv1());

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xmlData.getBytes(StandardCharsets.UTF_8)));

        NodeList items = doc.getElementsByTagName("item");
        for (int i = 0; i < items.getLength(); i++) {
            Element element = (Element) items.item(i);
            String cityName = element.getElementsByTagName("cityName").item(0).getTextContent();

            if (cityName.equals(airDTO.getRegionLv2())) {
                double pm10Value = Double.parseDouble(element.getElementsByTagName("pm10Value").item(0).getTextContent());
                double pm25Value = Double.parseDouble(element.getElementsByTagName("pm25Value").item(0).getTextContent());
                String date = element.getElementsByTagName("dataTime").item(0).getTextContent();
                return new AirDTO(airDTO.getRegionLv1(), airDTO.getRegionLv2(), pm10Value, pm25Value, date);
            }
        }
        return null;
    }

    //스케쥴러 등록
    @Scheduled(fixedDelay = 3600000)
    public void updateAirQuality() throws Exception {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        List<Air> staleData = airQualityRepository.findBySearchTimeBefore(Timestamp.valueOf(oneHourAgo));

        for (Air air : staleData) {
            AirDTO data = new AirDTO(air.getRegionLv1(), air.getRegionLv2());

            //API통신 및 데이터 업데이트
            AirDTO airDTO = parseAirQualityData(data);
            if (airDTO != null) {
               air.setPm10Value(airDTO.getPm10Value());
               air.setPm25Value(airDTO.getPm25Value());
               air.setSearchTime(Timestamp.valueOf(airDTO.getSearchTime()));

               airQualityRepository.save(air);
            }
        }
    }
}
