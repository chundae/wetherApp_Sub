package app.weatherapp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@Table(name = "AirQuality")
@Entity
@Getter
public class Air {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "pm10_value", nullable = false)
    private Double pm10Value;

    @Column(name = "pm25_value", nullable = false)
    private Double pm25Value;

    @Column(name = "searchTime", nullable = false)
    private Timestamp searchTime;
}
