package app.weatherapp.dto;

import app.weatherapp.domain.Location;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.sql.In;

@Getter
@Setter
public class RealDTO {


    private Double temperature; //온도
    private Double humidity; //습도
    private Integer rainFall; //강수확율
    private Double windSpeed; //풍속
    private Double windDirection; //풍향
    private String searchTime; //조회시간
    private Location location;
    //+RegionDTO 을 사용


    public RealDTO() {
    }

    public RealDTO(Double temperature, Double humidity, Double windSpeed, Double windDirection, Integer rainFall,
                   String baseTime, Location location) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.rainFall = rainFall;
        this.searchTime = baseTime;
        this.location = location;

    }

    @Override
    public String toString() {
        return "RealDTO{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                ", rainFall=" + rainFall +
                ", windSpeed=" + windSpeed +
                ", windDirection=" + windDirection +
                ", searchTime='" + searchTime + '\'' +
                ", locationX=" + location.getCodeY() +
                ", locationY=" + location.getCodeY() +
                '}';
    }
}
