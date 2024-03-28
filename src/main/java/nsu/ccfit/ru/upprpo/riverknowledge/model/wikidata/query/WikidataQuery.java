package nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.query;


import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.HasType;

public interface WikidataQuery extends HasType {
    String getWikidataQuery(String name);
}
