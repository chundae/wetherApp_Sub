package app.weatherapp.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;

@Table(name = "short_Time_weather")
@Entity
@Getter
public class DayWeather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ST_Id;

    @JoinColumn(name = "location_id")
    @ManyToOne
    private Location location;

    @Column(name = "temperature_hour")
    private Double temperatureHour;

    @Column(name = "min_temp")
    private Double minTemp;

    @Column(name = "max_temp")
    private Double maxTemp;

    @Column(name = "sky_des")
    private String skyDes;

    @Column(name = "check_date")
    private Timestamp checkDate;
}
