package nsu.ccfit.ru.upprpo.riverknowledge.service.query.impl;

import com.bordercloud.sparql.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.ccfit.ru.upprpo.riverknowledge.exception.SparqlQueryException;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;
import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.parser.WikidataResponseParser;
import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.parser.impl.URIAndLabelParser;
import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.query.WikidataQuery;
import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.query.impl.URIAndLabelWikidataQuery;
import nsu.ccfit.ru.upprpo.riverknowledge.service.query.QueryService;
import nsu.ccfit.ru.upprpo.riverknowledge.util.RiverPairKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService {
    private final List<WikidataResponseParser> parserList;

    @Value(value = "${wikidata.endpoint}")
    private String wikidataEndpoint;

    private final ThreadLocal<SparqlClient> client = ThreadLocal.withInitial(() -> {
        SparqlClient sparqlClient = new SparqlClient(true);
        sparqlClient.setEndpointRead(URI.create(wikidataEndpoint));
        return sparqlClient;
    });


    @Override
    public void riverLabelAndURIQuery(String name, Map<RiverPairKey, RiverEntity> rivers) {
        URIAndLabelWikidataQuery query = new URIAndLabelWikidataQuery();
        String querySelect = query.getWikidataQuery(name);

        try {
            SparqlResult result = client.get().query(querySelect);
            SparqlResultModel resultModel = result.getModel();
            log.info("Query " + query.getType() + " for river " + name + " done. Row count = " + resultModel.getRowCount());

            if (resultModel.getRowCount() > 0) {
                URIAndLabelParser parser = new URIAndLabelParser();
                parser.parse(resultModel, rivers);
                log.info(parser.getType() + " parse has finished. Rivers size = " + rivers.size());
            } else {
                log.warn("No information query" + query.getType() + " river " + name);
            }
        } catch (SparqlClientException e) {
            log.error("Error executing request SPARQL: {}", (Object) e.getSuppressed());
            throw new SparqlQueryException(e.getMessage());
        }
    }

    @Override
    public CompletableFuture<Void> executeQuery(String name, Map<RiverPairKey, RiverEntity> rivers, WikidataQuery query) {
        String querySelect = query.getWikidataQuery(name);

        return CompletableFuture.supplyAsync(() -> {
            try {
                return client.get().query(querySelect);
            } catch (SparqlClientException e) {
                log.error("Error executing request SPARQL for " + query.getType() + ": {}", e.getMessage());
                throw new SparqlQueryException(e.getMessage());
            }
        }).thenAccept(result -> {
            SparqlResultModel resultModel = result.getModel();
            log.info("Query " + query.getType() + " for river " + name + " done. Row count = " + resultModel.getRowCount());

            if (resultModel.getRowCount() > 0) {
                Optional<WikidataResponseParser> parser = getParserByType(query.getType());
                parser.ifPresentOrElse(p -> {
                    p.parse(resultModel, rivers);
                    log.info(p.getType() + " parse has finished. Rivers size = " + rivers.size());
                    client.remove();
                }, () -> log.error("No parser for " + query.getType() + " query"));
            } else {
                log.warn("No information query" + query.getType() + " river " + name);
            }
        });
    }

    private Optional<WikidataResponseParser> getParserByType(String type) {
        return parserList.stream()
                .filter(parser -> parser.getType().equals(type))
                .findFirst();
    }

}
