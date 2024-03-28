package nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.query.impl;

import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.query.WikidataQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WatershedAndLocatedWikidataQuery implements WikidataQuery {

    @Value(value = "${wikidata.watershed.and.located.type}")
    private String queryType;

    @Override
    public String getWikidataQuery(String name) {
        return String.format("""
                SELECT ?river ?label (group_concat(?locatedLabel;separator="/") as ?locates) ?watershed
                WHERE {
                  ?river wdt:P31 wd:Q4022;
                         wdt:P17 wd:Q159;
                         rdfs:label ?label;
                         wdt:P131 ?located.
                  FILTER (STRSTARTS(?label, "%s")).
                  OPTIONAL {
                    ?river wdt:P2053 ?watershed.
                    ?river wdt:P131 ?located.
                  }
                  SERVICE wikibase:label {
                    bd:serviceParam wikibase:language "ru,en".
                    ?located rdfs:label ?locatedLabel.
                  }
                } GROUP BY ?river ?label ?watershed having(count(?located) > 0)""", name);
    }

    @Override
    public String getType() {
        return queryType;
    }

}
