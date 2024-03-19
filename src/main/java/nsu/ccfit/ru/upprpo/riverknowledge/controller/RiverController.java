package nsu.ccfit.ru.upprpo.riverknowledge.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.geojson.result.GeoServiceResult;
import nsu.ccfit.ru.upprpo.riverknowledge.service.RiverService;
import nsu.ccfit.ru.upprpo.riverknowledge.service.geojson.GeoFeaturesService;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/river/", produces = MediaType.APPLICATION_JSON_VALUE)
public class RiverController {
    private final RiverService riverService;
    private final GeoFeaturesService geoFeaturesService;

    @GetMapping("search/{name}")
    public List<RiverEntity> getRivers(@PathVariable(value = "name")
                                       @NotNull @NotBlank @Length(min=2) String riverName) {
        return riverService.getRiver(riverName);
    }

    @GetMapping("geo/info/{name}")
    public GeoServiceResult getRiverGeoInfo(@PathVariable(value = "name")
                                            @NotNull @NotBlank @Length(min=2) String riverName) {
        return geoFeaturesService.getRiverInfoFromGeoJson(riverName);
    }
}
