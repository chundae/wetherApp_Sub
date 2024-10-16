package app.weatherapp.domain;

import jakarta.persistence.*;
import lombok.Cleanup;

@Table(name = "location")
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;


}
