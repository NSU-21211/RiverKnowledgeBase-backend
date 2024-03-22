package nsu.ccfit.ru.upprpo.riverknowledge.service;

import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;
import nsu.ccfit.ru.upprpo.riverknowledge.util.RiverPairKey;

import java.util.Map;
import java.util.Optional;

public interface RiverService {
    Map<RiverPairKey, RiverEntity> getRiverInfo(String name);

    Optional<RiverEntity> getRiverByName(String name);
}
