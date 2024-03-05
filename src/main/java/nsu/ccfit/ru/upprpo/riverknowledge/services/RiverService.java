package nsu.ccfit.ru.upprpo.riverknowledge.services;

import nsu.ccfit.ru.upprpo.riverknowledge.models.entity.RiverEntity;

import java.util.List;

public interface RiverService {
    List<RiverEntity> getRiver(String name);
}
