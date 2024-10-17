package app.weatherapp.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class AirDTO {

    private String regionLv1; //광역시
    private String regionLv2; //시군도
    private double pm25Value; //초미세먼지
    private double pm10Value; //미세먼지데이터
    private String searchTime; //조회시간
    private Long locationId; //행정 지역코드
    public AirDTO() {
    }

    public AirDTO(String regionLv1, String regionLv2, Long locationId) {
        this.regionLv1 = regionLv1;
        this.regionLv2 = regionLv2;
        this.locationId = locationId;
    }

    public AirDTO(String regionLv1, String regionLv2) {
        this.regionLv1 = regionLv1;
        this.regionLv2 = regionLv2;
    }

    public AirDTO(String regionLv1, String regionLv2, double pm10Value, double pm25Value, String searchTime) {
        this.regionLv1 = regionLv1;
        this.regionLv2 = regionLv2;
        this.pm25Value = pm25Value;
        this.pm10Value = pm10Value;
        this.searchTime = searchTime;
    }
}