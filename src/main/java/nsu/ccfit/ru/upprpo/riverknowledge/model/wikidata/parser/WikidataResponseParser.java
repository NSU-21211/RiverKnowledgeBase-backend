package nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.parser;

import com.bordercloud.sparql.SparqlResultModel;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;
import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.HasType;
import nsu.ccfit.ru.upprpo.riverknowledge.util.RiverPairKey;

import java.util.Map;

public interface WikidataResponseParser extends HasType {
    void parse(SparqlResultModel resultModel, Map<RiverPairKey, RiverEntity> rivers);
}
