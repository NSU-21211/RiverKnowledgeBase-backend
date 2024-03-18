package nsu.ccfit.ru.upprpo.riverknowledge.controller;

import lombok.RequiredArgsConstructor;
import nsu.ccfit.ru.upprpo.riverknowledge.model.dto.RiverDTO;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;
import nsu.ccfit.ru.upprpo.riverknowledge.service.RiverService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverName;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.geojson.result.GeoServiceResult;
import nsu.ccfit.ru.upprpo.riverknowledge.service.geojson.GeoFeaturesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/river/")
public class RiverController {
    private final RiverService riverService;
    private final GeoFeaturesService geoFeaturesService;
    private final ModelMapper mapper;

    @GetMapping("/search")
    public Map<URI, RiverDTO> getRivers(@RequestBody RiverName riverName) {
        Map<URI, RiverEntity> rivers = riverService.getRiverInfo(riverName.getName());
        return rivers.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> mapper.map(entry.getValue(), RiverDTO.class)
                ));
    }
    @GetMapping("geo/info")
    public GeoServiceResult getRiverGeoInfo(@RequestBody RiverName riverName) {
        return geoFeaturesService.getRiverInfoFromGeoJson(riverName.getName());
    }
}
