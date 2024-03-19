package nsu.ccfit.ru.upprpo.riverknowledge.controller;

import lombok.RequiredArgsConstructor;
import nsu.ccfit.ru.upprpo.riverknowledge.model.dto.RiverDTO;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverName;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.geojson.result.GeoServiceResult;
import nsu.ccfit.ru.upprpo.riverknowledge.service.RiverService;
import nsu.ccfit.ru.upprpo.riverknowledge.service.geojson.GeoFeaturesService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/river")
public class RiverController {
    private final RiverService riverService;
    private final GeoFeaturesService geoFeaturesService;
    private final ModelMapper mapper;

    @GetMapping("/search")
    public String getRivers(@RequestBody RiverName riverName, Model model) {
        Map<URI, RiverEntity> rivers = riverService.getRiverInfo(riverName.getName());
        Map<URI, RiverDTO> riverDTOMap = rivers.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> mapper.map(entry.getValue(), RiverDTO.class)
                ));
        model.addAttribute("riverDTOmap", riverDTOMap);
        return "river_list";
    }

    @GetMapping("/get")
    public String getRiver(@RequestParam("river") String riverName, Model model) {
        Optional<RiverEntity> river = riverService.getRiverByName(riverName);
        model.addAttribute("river", river);
        return "river_details";
    }

    @GetMapping("geo/info")
    public GeoServiceResult getRiverGeoInfo(@RequestBody RiverName riverName) {
        return geoFeaturesService.getRiverInfoFromGeoJson(riverName.getName());
    }
}
