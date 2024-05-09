package joluphosoin.tennisfunserver.user.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.geo.Point;

import java.util.Map;


@Getter
@Setter
public class LocationUpdateDto {
    private Point location;
    private Double maxDistance;

    @JsonProperty("location")
    public void setLocation(Map<String, Double> jsonLocation) {
        double latitude = jsonLocation.get("latitude");
        double longitude = jsonLocation.get("longitude");
        this.location = new Point(longitude, latitude); // Point는 경도(longitude), 위도(latitude) 순서입니다.
    }
}
