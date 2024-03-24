package nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.query.impl;

import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.query.WikidataQuery;
import org.springframework.stereotype.Component;

@Component
public class CountryAndMouthWikidataQuery implements WikidataQuery {
    @Override
    public String getWikidataQuery(String name) {
        return String.format("""
                SELECT ?river ?label (group_concat(?mouthLabel; separator="/") as ?mouths) ?countryLabel
                WHERE {
                  ?river wdt:P31 wd:Q4022;
                         wdt:P17 wd:Q159;
                         rdfs:label ?label;
                         wdt:P403 ?mouth;
                         wdt:P17 ?country.
                  FILTER (STRSTARTS(?label, "%s")).
                  SERVICE wikibase:label {
                    bd:serviceParam wikibase:language "ru,en".
                    ?mouth rdfs:label ?mouthLabel.
                    ?country rdfs:label ?countryLabel.
                  }
                } GROUP BY ?river ?label ?countryLabel HAVING (COUNT(?mouth) > 0)""", name);
    }

    @Override
    public String getType() {
        return "country-mouth";
    }

}
