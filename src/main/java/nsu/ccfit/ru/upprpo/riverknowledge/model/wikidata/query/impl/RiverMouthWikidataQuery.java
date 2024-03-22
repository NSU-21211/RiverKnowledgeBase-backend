package nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.query.impl;

import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.query.WikidataQuery;
import org.springframework.stereotype.Component;

@Component
public class RiverMouthWikidataQuery implements WikidataQuery {
    public String getWikidataQuery(String name) {
        return String.format("""
                SELECT DISTINCT ?river ?label ?mouthLabel
                WHERE {
                  ?river wdt:P31 wd:Q4022;
                         wdt:P17 wd:Q159;
                         rdfs:label ?label.
                  FILTER (STRSTARTS(?label, "%s")).
                  OPTIONAL {
                    ?river wdt:P403 ?mouth.
                  }
                  SERVICE wikibase:label {
                    bd:serviceParam wikibase:language "ru,en".
                  }
                }""", name);
    }

    @Override
    public String getType() {
        return "mouth";
    }
}
