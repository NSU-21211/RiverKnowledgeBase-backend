package nsu.ccfit.ru.upprpo.riverknowledge.model.entity.geojson.result;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class GeoServiceResult {
    private final int inflowsCount;
    private final double riverLengthKm;
    private final List<DistancesFromRiverMouth> distancesList;

}
