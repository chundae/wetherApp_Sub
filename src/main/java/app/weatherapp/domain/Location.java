package app.weatherapp.domain;

import com.sun.tools.jconsole.JConsoleContext;
import jakarta.persistence.*;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;

@Table(name = "location")
@Entity
@Getter
@Setter
public class Location {

    @Id
    @Column(name = "location_id")
    private Long locationId; //행정지역코드

    @Column(name = "region_lv1")
    private String regionLv1; //광역시

    @Column(name = "region_lv2")
    private String regionLv2; //시도

    @Column(name = "region_lv3")
    private String regionLv3; //동면

    @Column(name = "code_x")
    private String codeX; //x좌표

    @Column(name = "code_y")
    private String codeY; //Y좌표

    public Location(String regionLv1, String regionLv2) {
        this.regionLv1 = regionLv1;
        this.regionLv2 = regionLv2;
    }

    public Location() {

    }
}
