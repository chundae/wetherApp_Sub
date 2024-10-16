package app.weatherapp.service;

import app.weatherapp.dto.AirDTO;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class AirQualityService {


    public AirDTO parseAirQualityData(String xmlData, String regionLv2, String regionLv1) throws Exception{
        if(xmlData == null || xmlData.isEmpty()){
            throw new IllegalAccessException("XML data is null or empty");
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xmlData.getBytes(StandardCharsets.UTF_8)));

        NodeList items = doc.getElementsByTagName("item");
        for(int i = 0; i < items.getLength(); i++) {
            Element element = (Element) items.item(i);
            String cityName = element.getElementsByTagName("cityName").item(0).getTextContent();

            if(cityName.equals(regionLv2)) {
                double pm10Value = Double.parseDouble(element.getElementsByTagName("pm10Value").item(0).getTextContent());
                double pm25Value = Double.parseDouble(element.getElementsByTagName("pm25Value").item(0).getTextContent());
                String date = element.getElementsByTagName("dataTime").item(0).getTextContent();
                return new AirDTO(regionLv1, regionLv2, pm10Value, pm25Value, date);
            }
        }
        return null;
    }


}
