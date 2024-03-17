package nsu.ccfit.ru.upprpo.riverknowledge.controller;

import lombok.RequiredArgsConstructor;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverName;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.geojson.result.GeoServiceResult;
import nsu.ccfit.ru.upprpo.riverknowledge.service.RiverService;
import nsu.ccfit.ru.upprpo.riverknowledge.service.geojson.GeoFeaturesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/river/")
public class RiverController {
    private final RiverService riverService;
    private final GeoFeaturesService geoFeaturesService;

    @GetMapping("search")
    public List<RiverEntity> getRivers(@RequestBody RiverName riverName) {
        return riverService.getRiver(riverName.getName());
    }

    @GetMapping("geo/info")
    public GeoServiceResult getRiverGeoInfo(@RequestBody RiverName riverName) {
        return geoFeaturesService.getRiverInfoFromGeoJson(riverName.getName());
    }
}
