package nsu.ccfit.ru.upprpo.riverknowledge.service.river;

import nsu.ccfit.ru.upprpo.riverknowledge.model.dto.RiverDTO;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;

import java.util.List;
import java.util.Optional;

public interface RiverService {
    List<RiverDTO> getRiverInfo(String name);

    Optional<RiverEntity> getRiverByName(String name);
}
