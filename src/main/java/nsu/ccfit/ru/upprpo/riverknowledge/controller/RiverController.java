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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/river/")
public class RiverController {
    private final RiverService riverService;
    private final ModelMapper mapper;

    @GetMapping("/search")
    public Map<URI, RiverDTO> getRivers(@RequestParam(name = "river") String name) {
        Map<URI, RiverEntity> rivers = riverService.getRiverInfo(name);
        return rivers.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> mapper.map(entry.getValue(), RiverDTO.class)
                ));
    }

}
