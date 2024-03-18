package nsu.ccfit.ru.upprpo.riverknowledge.service;

import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;

import java.util.List;

public interface RiverService {
    List<RiverEntity> getRiver(String name);
}
