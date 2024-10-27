package app.weatherapp.service;

import app.weatherapp.domain.Location;
import app.weatherapp.domain.RealWeather;
import app.weatherapp.dto.RealDTO;
import app.weatherapp.explorer.APIExplorer;
import app.weatherapp.repository.RealWeatherRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WeatherService {

    private final RealWeatherRepository weatherRepository;
    private final APIExplorer apiExplorer;

    public WeatherService(RealWeatherRepository weatherRepository, APIExplorer apiExplorer) {
        this.weatherRepository = weatherRepository;
        this.apiExplorer = apiExplorer;
    }

    //API 통신
    public RealDTO getCurrentWeather(Location location) throws Exception {
        String xmlData = apiExplorer.getRealTimeWeatherData(location);//지역정보를 변환해 API통신

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xmlData.getBytes(StandardCharsets.UTF_8)));

        NodeList items = doc.getElementsByTagName("item");
        Double temperature = null;
        Double humidity = null;
        Integer rainFall = null;
        Double windSpeed = null;
        Double windDirection = null;
        String baseTime = null;


        for (int i = 0; i < items.getLength(); i++) {
            Node item = items.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) item;

                String category = element.getElementsByTagName("category").item(0).getTextContent();
                String obsrValue = element.getElementsByTagName("obsrValue").item(0).getTextContent();
                baseTime = element.getElementsByTagName("baseTime").item(0).getTextContent();

                switch (category) {
                    case "T1H" -> temperature = Double.parseDouble(obsrValue);
                    case "REH" -> humidity = Double.parseDouble(obsrValue);
                    case "PTY" -> rainFall = Integer.parseInt(obsrValue);
                    case "VEC" -> windSpeed = Double.parseDouble(obsrValue);
                    case "WSD" -> windDirection = Double.parseDouble(obsrValue);
                }
            }
        }

        return new RealDTO(temperature, humidity, windSpeed, windDirection, rainFall, baseTime, location);
    }

    //데이터 변환
    public void saveRealWeather(RealDTO realDTO) throws Exception {
        RealWeather realWeather = new RealWeather();
        realWeather.setLocation(realDTO.getLocation());
        realWeather.setTemperature(realDTO.getTemperature());
        realWeather.setHumidity(realDTO.getHumidity());
        realWeather.setWindSpeed(realDTO.getWindSpeed());
        realWeather.setRainFall(realDTO.getRainFall());
        realWeather.setWindDirection(realDTO.getWindDirection());
        realWeather.setCheckTime(Timestamp.valueOf(realDTO.getSearchTime()));

        weatherRepository.save(realWeather);
    }
}
