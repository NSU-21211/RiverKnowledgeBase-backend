package nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.query.impl;

import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.query.WikidataQuery;
import org.springframework.beans.factory.annotation.Value;

public class URIAndLabelWikidataQuery implements WikidataQuery {

    @Value(value = "${wikidata.uri.and.label.type}")
    private String queryType;

    public String getWikidataQuery(String name) {
        return String.format("""
                SELECT DISTINCT ?river ?label
                WHERE {
                  ?river wdt:P31 wd:Q4022;
                         wdt:P17 wd:Q159;
                         rdfs:label ?label.
                  FILTER (STRSTARTS(?label, "%s")).
                  SERVICE wikibase:label {
                    bd:serviceParam wikibase:language "ru,en".
                  }
                }""", name);
    }

    @Override
    public String getType() {
        return queryType;
    }

}
