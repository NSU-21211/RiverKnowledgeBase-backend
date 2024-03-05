package nsu.ccfit.ru.upprpo.riverknowledge.controllers;

import lombok.RequiredArgsConstructor;
import nsu.ccfit.ru.upprpo.riverknowledge.models.entity.RiverEntity;
import nsu.ccfit.ru.upprpo.riverknowledge.services.RiverService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/river/")
public class RiverController {
    private final RiverService riverService;

    @GetMapping("/get")
    public List<RiverEntity> getRivers(@RequestParam(name = "river") String name) {
        return riverService.getRiver(name);
    }

}
