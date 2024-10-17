package app.weatherapp.explorer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Component
public class AirExplorer {

    @Value("${service.api.key1}")
    private String apiKey;

    public String getAirQualityData(String regionLv1) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnStatsSvc/getCtprvnMesureSidoLIst");

        urlBuilder.append("?" + URLEncoder.encode("sidoName", "UTF-8") + "=" + URLEncoder.encode(regionLv1, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("searchCondition", "UTF-8") + "=DAILY");
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=1");
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=100");
        urlBuilder.append("&" + URLEncoder.encode("returnType", "UTF-8") + "=xml");
        urlBuilder.append("&" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + apiKey);

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");

        BufferedReader rd;
        if (conn.getResponseCode() == 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }else{
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
