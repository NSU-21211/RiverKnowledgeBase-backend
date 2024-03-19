package nsu.ccfit.ru.upprpo.riverknowledge.service.impl;

import com.bordercloud.sparql.SparqlClient;
import com.bordercloud.sparql.SparqlClientException;
import com.bordercloud.sparql.SparqlResult;
import com.bordercloud.sparql.SparqlResultModel;
import lombok.extern.slf4j.Slf4j;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;
import nsu.ccfit.ru.upprpo.riverknowledge.model.query.RiverInfoQuery;
import nsu.ccfit.ru.upprpo.riverknowledge.model.query.RiverTributariesQuery;
import nsu.ccfit.ru.upprpo.riverknowledge.model.response.ResponseParser;
import nsu.ccfit.ru.upprpo.riverknowledge.service.RiverService;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class RiverServiceImpl implements RiverService {
    private static final String WIKIDATA_ENDPOINT = "https://query.wikidata.org/sparql";
    private final SparqlClient client = new SparqlClient(false);
    private final ResponseParser parser = new ResponseParser();
    private final Map<URI, RiverEntity> rivers = new HashMap<>();

    @Override
    public Map<URI, RiverEntity> getRiverInfo(String name) {
        String querySelect = RiverInfoQuery.getRiverQuery(name);
        client.setEndpointRead(URI.create(WIKIDATA_ENDPOINT));
        try {
            SparqlResult result = client.query(querySelect);
            log.info("Запрос на получение информации для реки " + name + " выполнен.");

            SparqlResultModel resultModel = result.getModel();
            if (resultModel.getRowCount() > 0) {
                parser.parseRiverInfo(resultModel, rivers);

                getRiverTributaries(name);
            } else {
                log.warn("Нет информации для реки " + name);
            }
        } catch (SparqlClientException exception) {
            log.error("Ошибка при выполнении запроса SPARQL: {}", exception.getLocalizedMessage());
            throw new RuntimeException(exception);
        }

        return rivers;
    }

    @Override
    public void getRiverTributaries(String name) {
        String querySelect = RiverTributariesQuery.getRiverTributariesQuery(name);

        try {
            SparqlResult result = client.query(querySelect);
            log.info("Запрос на получение притоков для реки " + name + " выполнен.");

            SparqlResultModel resultModel = result.getModel();
            if (resultModel.getRowCount() > 0) {
                parser.parseRiverTributaries(resultModel, rivers);
            } else {
                log.warn("Нет притоков для реки " + name);
            }
        } catch (SparqlClientException exception) {
            log.error("Ошибка при выполнении запроса SPARQL: {}", exception.getLocalizedMessage());
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Optional<RiverEntity> getRiverByName(String name) {
        return rivers.values().stream()
                .filter(river -> river.getLabel().equals(name))
                .findFirst();
    }

}
