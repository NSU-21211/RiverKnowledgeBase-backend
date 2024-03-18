package nsu.ccfit.ru.upprpo.riverknowledge.service;

import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;

import java.net.URI;
import java.util.Map;

public interface RiverService {
    Map<URI, RiverEntity> getRiverInfo(String name);

    void getRiverTributaries(String name);
}
