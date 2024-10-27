package app.weatherapp.explorer;

import app.weatherapp.domain.Location;
import app.weatherapp.dto.RegionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Component
public class APIExplorer {

    @Value("${service.api.key1}")
    private String AirKey;

    @Value("${service.api.key2}")
    private String WeatherKey;

    //미세먼지 API 연결 및 responseDate Return
    public String getAirQualityData(String regionLv1) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnStatsSvc/getCtprvnMesureSidoLIst");

        urlBuilder.append("?" + URLEncoder.encode("sidoName", "UTF-8") + "=" + URLEncoder.encode(regionLv1, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("searchCondition", "UTF-8") + "=DAILY");
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=1");
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=100");
        urlBuilder.append("&" + URLEncoder.encode("returnType", "UTF-8") + "=xml");
        urlBuilder.append("&" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + AirKey);

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");

        BufferedReader rd;
        if (conn.getResponseCode() == 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder response = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
        }

        rd.close();
        conn.disconnect();
        return response.toString();
    }

    //날씨 API 연결 및 responseData Return
    public String getRealTimeWeatherData(Location location) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        LocalDate localDate = now.toLocalDate();

        // base_date는 "YYYYMMDD" 형식으로 변환
        String baseDate = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // base_time은 정시로 맞추기 (정시에 맞지 않으면 이전 정시로 설정)
        LocalTime localTime = now.toLocalTime().truncatedTo(ChronoUnit.HOURS);
        if (localTime.getHour() == 0) {
            localTime = LocalTime.of(23, 0);  // 자정일 경우 전날 2300으로 설정
        } else {
            localTime = localTime.minusHours(1);  // 현재 시간에서 1시간 전 정시로 설정
        }

        String baseTime = localTime.format(DateTimeFormatter.ofPattern("HHmm"));

        // URL 생성
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst");

        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + WeatherKey);
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("XML", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(location.getCodeX()), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(location.getCodeY()), "UTF-8"));

        // URL 객체 생성
        URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder response = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
        }
        rd.close();
        conn.disconnect();
        return response.toString();
    }
}
