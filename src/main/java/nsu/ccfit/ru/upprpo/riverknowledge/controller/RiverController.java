package nsu.ccfit.ru.upprpo.riverknowledge.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nsu.ccfit.ru.upprpo.riverknowledge.model.dto.RiverDTO;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.geojson.result.GeoServiceResult;
import nsu.ccfit.ru.upprpo.riverknowledge.service.RiverService;
import nsu.ccfit.ru.upprpo.riverknowledge.service.geojson.GeoFeaturesService;
import nsu.ccfit.ru.upprpo.riverknowledge.util.RiverPairKey;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/river")
//@RequestMapping(value = "/api/river/", produces = MediaType.APPLICATION_JSON_VALUE)
public class RiverController {
    private final RiverService riverService;
    private final GeoFeaturesService geoFeaturesService;
    private final ModelMapper mapper;

    @GetMapping("/search/{name}")
    public String getRivers(@PathVariable(value = "name")
                            @NotNull @NotBlank @Length(min = 2) String riverName, Model model) {
        Map<RiverPairKey, RiverEntity> rivers = riverService.getRiverInfo(riverName);
        Map<RiverPairKey, RiverDTO> riverDTOMap = rivers.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> mapper.map(entry.getValue(), RiverDTO.class)
                ));
        model.addAttribute("riverDTOmap", riverDTOMap);
        return "river_list";
    }

    @GetMapping("/get/{name}")
    public String getRiver(@PathVariable(value = "name")
                           @NotNull @NotBlank @Length(min = 2) String riverName, Model model) {
        Optional<RiverEntity> river = riverService.getRiverByName(riverName);
        model.addAttribute("river", river);
        return "river_details";
    }

    @GetMapping("geo/info/{name}")
    public GeoServiceResult getRiverGeoInfo(@PathVariable(value = "name")
                                            @NotNull @NotBlank @Length(min = 2) String riverName) {
        return geoFeaturesService.getRiverInfoFromGeoJson(riverName);
    }
}
