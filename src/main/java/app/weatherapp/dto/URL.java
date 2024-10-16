package app.weatherapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class URL {

    private String url;

    public URL(String url) {
        this.url = url;
    }

    public URL() {
    }
}
