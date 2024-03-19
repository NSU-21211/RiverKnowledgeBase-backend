package nsu.ccfit.ru.upprpo.riverknowledge.model.entity.geojson.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class GeoServiceResult {
    @JsonProperty(value = "inflows_count")
    private final int inflowsCount;
    @JsonProperty(value = "length_km")
    private final double riverLengthKm;
    @JsonProperty(value = "distances_to_inflows")
    private final List<DistancesFromRiverMouth> distancesList;
}
