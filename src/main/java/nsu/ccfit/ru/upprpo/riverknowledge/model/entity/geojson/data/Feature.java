package nsu.ccfit.ru.upprpo.riverknowledge.model.entity.geojson.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Feature {
    @JsonProperty(value = "type")
    private String featureType;
    @JsonProperty(value = "properties")
    private FeatureProperty featureProperty;
    @JsonProperty(value = "geometry")
    private FeatureGeometry featureGeometry;
}
