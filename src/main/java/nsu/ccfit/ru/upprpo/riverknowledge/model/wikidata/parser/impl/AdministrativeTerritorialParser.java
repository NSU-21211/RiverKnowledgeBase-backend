package nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.parser.impl;

import com.bordercloud.sparql.SparqlResultModel;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;
import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.parser.WikidataResponseParser;
import nsu.ccfit.ru.upprpo.riverknowledge.util.RiverPairKey;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Map;

@Component
public class AdministrativeTerritorialParser implements WikidataResponseParser {
    public void parse(SparqlResultModel resultModel, Map<RiverPairKey, RiverEntity> rivers) {
        for (int i = 0; i < resultModel.getRowCount(); ++i) {
            URI riverLink = URI.create(String.valueOf(resultModel.getRows().get(i).get("river")));
            String riverLabel = String.valueOf(resultModel.getRows().get(i).get("label"));
            RiverPairKey key = new RiverPairKey(riverLink, riverLabel);

            rivers.get(key).getAdministrativeTerritorial().add(String.valueOf(resultModel.getRows().get(i).get("locatedLabel")));
        }
    }

    @Override
    public String getType() {
        return "administrativeTerritorial";
    }
}
