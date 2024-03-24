package nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.parser.impl;

import com.bordercloud.sparql.SparqlResultModel;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;
import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.parser.WikidataResponseParser;
import nsu.ccfit.ru.upprpo.riverknowledge.util.RiverPairKey;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Component
public class ImageAndTributariesParser implements WikidataResponseParser {
    @Override
    public void parse(SparqlResultModel resultModel, Map<RiverPairKey, RiverEntity> rivers) {
        for (int i = 0; i < resultModel.getRowCount(); ++i) {
            URI riverLink = URI.create(String.valueOf(resultModel.getRows().get(i).get("river")));
            String riverLabel = String.valueOf(resultModel.getRows().get(i).get("label"));
            URI image = URI.create(String.valueOf(resultModel.getRows().get(i).get("image")));
            String modelTributaries = String.valueOf(resultModel.getRows().get(i).get("tributaries"));

            String[] tributaries = modelTributaries.split("/");
            RiverPairKey key = new RiverPairKey(riverLink, riverLabel);
            rivers.get(key).setImage(image);
            rivers.get(key).setTributaries(new HashSet<>(List.of(tributaries)));
        }
    }

    @Override
    public String getType() {
        return "image-tributaries";
    }
}
