package app.weatherapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionDTO {

    private String regionLv1; //광역시
    private String regionLv2; //시군구

    public RegionDTO(String regionLv1, String regionLv2) {
        this.regionLv1 = regionLv1;
        this.regionLv2 = regionLv2;
    }
}
