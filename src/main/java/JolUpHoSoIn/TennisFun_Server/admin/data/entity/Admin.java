package JolUpHoSoIn.TennisFun_Server.admin.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Document(collation = "admin")
@Getter
@AllArgsConstructor
@Builder
public class Admin {

    @MongoId
    private String id;

    private String businessNumber;
    private String accountNumber;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Point location;

    private String description;
    private String courtCount;
    private int revenue;

    private LocalDateTime openTime;
    private LocalDateTime closeTime;
}
