package nsu.ccfit.ru.upprpo.riverknowledge.service.geojson;

import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.geojson.result.GeoServiceResult;

public interface GeoFeaturesService {
    GeoServiceResult getRiverInfoFromGeoJson(String sourceRiver);
}
