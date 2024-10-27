package app.weatherapp.domain;

import com.sun.tools.jconsole.JConsoleContext;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;

@Table(name = "location")
@Entity
@Getter
@Setter
@AllArgsConstructor
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
    private Integer codeX; //x좌표

    @Column(name = "code_y")
    private Integer codeY; //Y좌표

    public Location(String regionLv1, String regionLv2) {
        this.regionLv1 = regionLv1;
        this.regionLv2 = regionLv2;
    }

    public Location() {

    }

    public Location(Integer codeX, Integer codeY) {
        this.codeX = codeX;
        this.codeY = codeY;
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", regionLv1='" + regionLv1 + '\'' +
                ", regionLv2='" + regionLv2 + '\'' +
                ", regionLv3='" + regionLv3 + '\'' +
                ", codeX='" + codeX + '\'' +
                ", codeY='" + codeY + '\'' +
                '}';
    }
}
