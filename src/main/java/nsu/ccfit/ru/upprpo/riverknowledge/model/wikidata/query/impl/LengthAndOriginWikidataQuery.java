package nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.query.impl;

import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.query.WikidataQuery;
import org.springframework.stereotype.Component;

@Component
public class LengthAndOriginWikidataQuery implements WikidataQuery {

    @Override
    public String getWikidataQuery(String name) {
        return String.format("""
                SELECT ?river ?label (group_concat(?originLabel;separator="/") as ?origins) ?length
                WHERE {
                  ?river wdt:P31 wd:Q4022;
                         wdt:P17 wd:Q159;
                         rdfs:label ?label;
                         wdt:P885 ?origin.
                  FILTER (STRSTARTS(?label, "%s")).
                  OPTIONAL {
                    ?river wdt:P885 ?origin.
                    ?river wdt:P2043 ?length.
                  }
                  SERVICE wikibase:label {
                    bd:serviceParam wikibase:language "ru,en".
                    ?origin rdfs:label ?originLabel.
                  }
                } GROUP BY ?river ?label ?length having(count(?origin) > 0)""", name);
    }

    @Override
    public String getType() {
        return "length-origin";
    }

}
