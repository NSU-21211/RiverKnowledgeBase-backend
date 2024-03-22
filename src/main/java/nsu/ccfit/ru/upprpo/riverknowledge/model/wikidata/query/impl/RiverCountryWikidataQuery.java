package nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.query.impl;

import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.query.WikidataQuery;
import org.springframework.stereotype.Component;

@Component
public class RiverCountryWikidataQuery implements WikidataQuery {
    public String getWikidataQuery(String name) {
        return String.format("""
                SELECT DISTINCT ?river ?label ?countryLabel
                WHERE {
                  ?river wdt:P31 wd:Q4022;
                         wdt:P17 wd:Q159;
                         rdfs:label ?label.
                  FILTER (STRSTARTS(?label, "%s")).
                  OPTIONAL {
                    ?river wdt:P17 ?country.
                  }
                  SERVICE wikibase:label {
                    bd:serviceParam wikibase:language "ru,en".
                  }
                }""", name);
    }

    @Override
    public String getType() {
        return "country";
    }
}
