package app.weatherapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionDTO {

    private String regionLv1; //광역시
    private String regionLv2; //시군구
    private String regionLv3;

    private Integer code_x;
    private Integer code_y;

    public RegionDTO() {
    }

    public RegionDTO(String regionLv1, String regionLv2) {
        this.regionLv1 = regionLv1;
        this.regionLv2 = regionLv2;
    }

    public RegionDTO(String regionLv1, String regionLv2, String regionLv3) {
        this.regionLv1 = regionLv1;
        this.regionLv2 = regionLv2;
        this.regionLv3 = regionLv3;
    }

    public RegionDTO(Integer code_x, Integer code_y) {
        this.code_x = code_x;
        this.code_y = code_y;
    }
}
