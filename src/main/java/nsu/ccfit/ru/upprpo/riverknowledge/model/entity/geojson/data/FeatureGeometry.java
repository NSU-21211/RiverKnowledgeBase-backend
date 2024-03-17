package nsu.ccfit.ru.upprpo.riverknowledge.model.entity.geojson.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureGeometry {
    @JsonProperty(value = "type")
    private String geometryType;
    @JsonProperty(value = "coordinates")
    private List<Object> geometryCoordinates;
}
