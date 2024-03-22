package nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.parser.impl;

import com.bordercloud.sparql.SparqlResultModel;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;
import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.parser.WikidataResponseParser;
import nsu.ccfit.ru.upprpo.riverknowledge.util.RiverPairKey;

import java.net.URI;
import java.util.Map;

public class URIAndLabelParser implements WikidataResponseParser {
    public void parse(SparqlResultModel resultModel, Map<RiverPairKey, RiverEntity> rivers) {
        for (int i = 0; i < resultModel.getRowCount(); ++i) {
            URI riverLink = URI.create(String.valueOf(resultModel.getRows().get(i).get("river")));
            String riverLabel = String.valueOf(resultModel.getRows().get(i).get("label"));

            RiverPairKey key = new RiverPairKey(riverLink, riverLabel);
            if (!rivers.containsKey(key)) {
                RiverEntity river = new RiverEntity();
                river.setRiverLink(riverLink);
                river.setLabel(riverLabel);
                rivers.put(key, river);
            }

        }
    }

    @Override
    public String getType() {
        return "uri-label";
    }
}
