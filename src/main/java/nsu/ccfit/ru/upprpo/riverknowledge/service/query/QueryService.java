package nsu.ccfit.ru.upprpo.riverknowledge.service.query;

import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;
import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.query.WikidataQuery;
import nsu.ccfit.ru.upprpo.riverknowledge.util.RiverPairKey;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface QueryService {
    void riverLabelAndURIQuery(String riverName, Map<RiverPairKey, RiverEntity> rivers);

    CompletableFuture<Void> executeQuery(String riverName, Map<RiverPairKey, RiverEntity> rivers, WikidataQuery query);
}
