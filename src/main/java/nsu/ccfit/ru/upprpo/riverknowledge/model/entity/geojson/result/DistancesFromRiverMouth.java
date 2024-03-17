package nsu.ccfit.ru.upprpo.riverknowledge.model.entity.geojson.result;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class DistancesFromRiverMouth {
    private final String sourceRiverName;
    private final String inflowName;
    private final double distanceFromMouthKm;
}
