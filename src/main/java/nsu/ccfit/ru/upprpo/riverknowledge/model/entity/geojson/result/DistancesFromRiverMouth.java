package nsu.ccfit.ru.upprpo.riverknowledge.model.entity.geojson.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class DistancesFromRiverMouth {
    @JsonProperty(value = "source_river")
    private final String sourceRiverName;
    @JsonProperty(value = "inflow")
    private final String inflowName;
    @JsonProperty(value = "distance_from_mouth_km")
    private final double distanceFromMouthKm;
}
