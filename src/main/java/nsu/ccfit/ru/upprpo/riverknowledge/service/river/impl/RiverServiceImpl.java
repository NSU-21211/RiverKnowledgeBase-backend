package nsu.ccfit.ru.upprpo.riverknowledge.service.river.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.ccfit.ru.upprpo.riverknowledge.model.dto.RiverDTO;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;
import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.query.WikidataQuery;
import nsu.ccfit.ru.upprpo.riverknowledge.service.query.QueryService;
import nsu.ccfit.ru.upprpo.riverknowledge.service.river.RiverService;
import nsu.ccfit.ru.upprpo.riverknowledge.util.RiverPairKey;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class RiverServiceImpl implements RiverService {
    private final Map<RiverPairKey, RiverEntity> rivers = new ConcurrentHashMap<>();
    private final QueryService queryService;
    private final List<WikidataQuery> wikidataQueryList;
    private final ModelMapper mapper;

    @Override
    public List<RiverDTO> getRiverInfo(String name) {
        queryService.riverLabelAndURIQuery(name, rivers);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (WikidataQuery query : wikidataQueryList) {
            CompletableFuture<Void> future = queryService.executeQuery(name, rivers, query);
            futures.add(future);
        }

        for (CompletableFuture<Void> future : futures) {
            future.join();
        }

        return rivers.values().stream()
                .map(entity -> mapper.map(entity, RiverDTO.class))
                .toList();
    }


    @Override
    public Optional<RiverEntity> getRiverByName(String name) {
        return rivers.values().stream()
                .filter(river -> river.getLabel().equals(name))
                .findFirst();
    }

}
