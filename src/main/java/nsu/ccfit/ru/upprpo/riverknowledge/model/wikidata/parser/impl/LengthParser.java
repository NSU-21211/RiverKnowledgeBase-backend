package nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.parser.impl;

import com.bordercloud.sparql.SparqlResultModel;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;
import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.parser.WikidataResponseParser;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Map;

@Component
public class LengthParser implements WikidataResponseParser {
    public void parse(SparqlResultModel resultModel, Map<URI, RiverEntity> rivers) {
        for (int i = 0; i < resultModel.getRowCount(); ++i) {
            URI riverLink = URI.create(String.valueOf(resultModel.getRows().get(i).get("river")));
            String riverLabel = String.valueOf(resultModel.getRows().get(i).get("label"));
            Integer length = Integer.valueOf(String.valueOf(resultModel.getRows().get(i).get("length")));

            if (rivers.containsKey(riverLink)) {
                RiverEntity river = rivers.get(riverLink);
                if (river.getLabel().equals(riverLabel) && river.getLabel().isEmpty()) {
                    rivers.get(riverLink).setLength(length);
                }
            } else {
                RiverEntity river = new RiverEntity();
                river.setLength(length);
                rivers.put(riverLink, river);
            }
        }
    }

    @Override
    public String getType() {
        return "length";
    }
}
