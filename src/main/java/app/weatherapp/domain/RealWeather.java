package app.weatherapp.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;

@Table(name = "real_time_weather")
@Entity
@Getter
public class RealWeather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ST_id;

    @JoinColumn(name = "location_id")
    @ManyToOne
    private Location location;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "humidity")
    private Double humidity;

    @Column(name = "rainFall")
    private Integer rainFall;

    @Column(name = "wind_speed")
    private Double windSpeed;

    @Column(name = "wind_direction")
    private Integer windDirection;

    @Column(name = "checkTime")
    private Timestamp checkTime;
}
