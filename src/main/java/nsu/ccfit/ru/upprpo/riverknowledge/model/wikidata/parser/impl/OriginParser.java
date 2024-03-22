package nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.parser.impl;

import com.bordercloud.sparql.SparqlResultModel;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;
import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.parser.WikidataResponseParser;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Map;

@Component
public class OriginParser implements WikidataResponseParser {
    public void parse(SparqlResultModel resultModel, Map<URI, RiverEntity> rivers) {
        for (int i = 0; i < resultModel.getRowCount(); ++i) {
            URI riverLink = URI.create(String.valueOf(resultModel.getRows().get(i).get("river")));
            String riverLabel = String.valueOf(resultModel.getRows().get(i).get("label"));

            if (rivers.containsKey(riverLink) && rivers.get(riverLink).getLabel().equals(riverLabel)) {
                RiverEntity river = rivers.get(riverLink);
                river.getOrigin().add(String.valueOf(resultModel.getRows().get(i).get("originLabel")));
            } else {
                RiverEntity river = new RiverEntity();
                river.getOrigin().add(String.valueOf(resultModel.getRows().get(i).get("originLabel")));
                rivers.put(riverLink, river);
            }
        }
    }

    @Override
    public String getType() {
        return "origin";
    }
}
