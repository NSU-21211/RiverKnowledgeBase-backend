package nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.parser.impl;

import com.bordercloud.sparql.SparqlResultModel;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;
import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.parser.WikidataResponseParser;
import nsu.ccfit.ru.upprpo.riverknowledge.util.RiverPairKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Component
public class LengthAndOriginParser implements WikidataResponseParser {

    @Value(value = "${wikidata.length.and.origin.type}")
    private String parserType;

    @Override
    public void parse(SparqlResultModel resultModel, Map<RiverPairKey, RiverEntity> rivers) {
        for (int i = 0; i < resultModel.getRowCount(); ++i) {
            URI riverLink = URI.create(String.valueOf(resultModel.getRows().get(i).get("river")));
            String riverLabel = String.valueOf(resultModel.getRows().get(i).get("label"));

            String modelLength = String.valueOf(resultModel.getRows().get(i).get("length"));
            Integer length = modelLength.equals("null") ? 0 : Integer.parseInt(modelLength);

            String modelOrigins = String.valueOf(resultModel.getRows().get(i).get("origins"));

            String[] origins = modelOrigins.split("/");
            RiverPairKey key = new RiverPairKey(riverLink, riverLabel);
            rivers.get(key).setLength(length);
            rivers.get(key).setOrigin(new HashSet<>(List.of(origins)));
        }
    }

    @Override
    public String getType() {
        return parserType;
    }
}
