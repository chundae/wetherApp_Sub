package app.weatherapp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Table(name = "AirQuality", uniqueConstraints = @UniqueConstraint(columnNames = {"regionLv1", "regionLv2"}))
@Entity
@Getter
@Setter
public class Air {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //고유키

    @Column(name = "region_lv1", nullable = false)
    private String regionLv1;

    @Column(name = "region_lv2", nullable = false)
    private String regionLv2;

    @Column(name = "pm10_value", nullable = false)
    private Double pm10Value; //미세먼지

    @Column(name = "pm25_value", nullable = false)
    private Double pm25Value; //초미세먼지

    @Column(name = "searchTime", nullable = false)
    private Timestamp searchTime; //데이터 조회시간
}
