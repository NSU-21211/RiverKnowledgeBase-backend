package nsu.ccfit.ru.upprpo.riverknowledge.service.impl;

import com.bordercloud.sparql.SparqlClient;
import com.bordercloud.sparql.SparqlClientException;
import com.bordercloud.sparql.SparqlResult;
import com.bordercloud.sparql.SparqlResultModel;
import lombok.extern.slf4j.Slf4j;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;
import nsu.ccfit.ru.upprpo.riverknowledge.model.query.RiverQuery;
import nsu.ccfit.ru.upprpo.riverknowledge.model.response.ResponseParser;
import nsu.ccfit.ru.upprpo.riverknowledge.service.RiverService;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RiverServiceImpl implements RiverService {
    private static final String WIKIDATA_ENDPOINT = "https://query.wikidata.org/sparql";
    private final SparqlClient client = new SparqlClient(false);
    private final ResponseParser parser = new ResponseParser();
    private List<RiverEntity> rivers = new ArrayList<>();

    @Override
    public List<RiverEntity> getRiver(String name) {
        String querySelect = RiverQuery.getRiverQuery(name);
        client.setEndpointRead(URI.create(WIKIDATA_ENDPOINT));

        try {
            SparqlResult result = client.query(querySelect);
            log.info("Запрос выполнен");

            SparqlResultModel resultModel = result.getModel();

            if (resultModel.getRowCount() > 0) {
                rivers = parser.parseSparqlResult(resultModel);
            } else {
                log.warn("Нет результатов запроса");
            }
        } catch (SparqlClientException exception) {
            log.error("Ошибка при выполнении запроса SPARQL: {}", exception.getLocalizedMessage());
            throw new RuntimeException(exception);
        }

        return rivers;
    }
}
