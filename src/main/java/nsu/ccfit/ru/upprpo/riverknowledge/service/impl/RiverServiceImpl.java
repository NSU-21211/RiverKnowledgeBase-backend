package nsu.ccfit.ru.upprpo.riverknowledge.service.impl;

import com.bordercloud.sparql.*;
import com.bordercloud.sparql.authorization.AuthorizationSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;
import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.parser.WikidataResponseParser;
import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.parser.impl.URIAndLabelParser;
import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.query.WikidataQuery;
import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.query.impl.RiverURIAndLabelQuery;
import nsu.ccfit.ru.upprpo.riverknowledge.service.RiverService;
import nsu.ccfit.ru.upprpo.riverknowledge.util.RiverPairKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class RiverServiceImpl implements RiverService {

    private final SparqlClient client = new SparqlClient(true);
    private final Map<RiverPairKey, RiverEntity> rivers = new ConcurrentHashMap<>();
    private final List<WikidataQuery> wikidataQueryList;
    private final List<WikidataResponseParser> parserList;

    @Value(value = "${wikidata.endpoint}")
    private String wikidataEndpoint;

    @Override
    public Map<RiverPairKey, RiverEntity> getRiverInfo(String name) {
        client.setEndpointRead(URI.create(wikidataEndpoint));
        client.setMethodHTTPRead(Method.GET);

        riverLabelAndURIQuery(name);

        for (WikidataQuery wikidataQuery : wikidataQueryList) {
            String querySelect = wikidataQuery.getWikidataQuery(name);

            CompletableFuture.supplyAsync(() -> {
                try {
                    return client.query(querySelect);
                } catch (SparqlClientException e) {
                    log.error("Error executing request SPARQL for " + wikidataQuery.getType() + ": {}", e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }).thenAccept(result -> {
                SparqlResultModel resultModel = result.getModel();
                log.info("Query " + wikidataQuery.getType() + " for river " + name + " done. Row count = " + resultModel.getRowCount());

                if (resultModel.getRowCount() > 0) {
                    Optional<WikidataResponseParser> parser = getParserByType(wikidataQuery.getType());
                    if (parser.isPresent()) {
                        parser.get().parse(resultModel, rivers);
                        log.info(parser.get().getType() + " parse has finished. Rivers size = " + rivers.size());
                    } else {
                        log.error("No parser for " + wikidataQuery.getType() + " query");
                    }
                } else {
                    log.warn("No information for river " + name);
                }

            });

        }

        return rivers;
    }

    private void riverLabelAndURIQuery(String riverName) {
        RiverURIAndLabelQuery query = new RiverURIAndLabelQuery();
        String querySelect = query.getWikidataQuery(riverName);

        try {
            SparqlResult result = client.query(querySelect);
            SparqlResultModel resultModel = result.getModel();
            log.info("Query " + query.getType() + " for river " + riverName + " done. Row count = " + resultModel.getRowCount());

            if (resultModel.getRowCount() > 0) {
                URIAndLabelParser parser = new URIAndLabelParser();
                parser.parse(resultModel, rivers);
                log.info(parser.getType() + " parse has finished. Rivers size = " + rivers.size());
            } else {
                log.warn("No information for river " + riverName);
            }
        } catch (SparqlClientException e) {
            log.error("Error executing request SPARQL: {}", (Object) e.getSuppressed());
            throw new RuntimeException(e);
        }

    }

    private Optional<WikidataResponseParser> getParserByType(String type) {
        return parserList.stream()
                .filter(parser -> parser.getType().equals(type))
                .findFirst();
    }

    @Override
    public Optional<RiverEntity> getRiverByName(String name) {
        return rivers.values().stream()
                .filter(river -> river.getLabel().equals(name))
                .findFirst();
    }

}
