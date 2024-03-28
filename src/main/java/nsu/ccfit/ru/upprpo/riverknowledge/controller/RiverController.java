package nsu.ccfit.ru.upprpo.riverknowledge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nsu.ccfit.ru.upprpo.riverknowledge.model.dto.RiverDTO;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.geojson.result.GeoServiceResult;
import nsu.ccfit.ru.upprpo.riverknowledge.service.geojson.GeoFeaturesService;
import nsu.ccfit.ru.upprpo.riverknowledge.service.river.RiverService;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Tag(name = "River controller", description = "Эндпоинты для получения рек")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/river/", produces = MediaType.APPLICATION_JSON_VALUE)
public class RiverController {
    private final RiverService riverService;
    private final GeoFeaturesService geoFeaturesService;

    @Operation(
            summary = "Получение списка рек",
            description = "Проверяет введёное название реки и при успехе отправляет запрос. Ответом является список DTO"
    )
    @GetMapping("/search/{name}")
    public List<RiverDTO> getRivers(@PathVariable(value = "name")
                                    @NotNull @NotBlank @Length(min = 2) String riverName) {
        return riverService.getRiverInfo(riverName);
    }

    @Operation(
            summary = "Получение подробной информации конкретной реки",
            description = "Проверяет назавние реки и при успехе отображает полную информацию о реке"
    )
    @GetMapping("/get/{name}")
    public Optional<RiverEntity> getRiver(@PathVariable(value = "name")
                                          @NotNull @NotBlank @Length(min = 2) String riverName) {
        return riverService.getRiverByName(riverName);
    }

    @Operation(
            summary = "Получение геометрической информации об указанной реке",
            description = "Валидирует переданное название и посылает overpass запрос на получение geojson, после преобразует её"
    )
    @GetMapping("geo/info/{name}")
    public GeoServiceResult getRiverGeoInfo(@PathVariable(value = "name")
                                            @NotNull @NotBlank @Length(min = 2) String riverName) {
        return geoFeaturesService.getRiverInfoFromGeoJson(riverName);
    }
}
