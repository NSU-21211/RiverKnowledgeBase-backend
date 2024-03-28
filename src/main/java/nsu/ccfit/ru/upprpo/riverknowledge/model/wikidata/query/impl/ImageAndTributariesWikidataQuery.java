package nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.query.impl;

import nsu.ccfit.ru.upprpo.riverknowledge.model.wikidata.query.WikidataQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImageAndTributariesWikidataQuery implements WikidataQuery {

    @Value(value = "${wikidata.image.and.tributaries.type}")
    private String queryType;

    @Override
    public String getWikidataQuery(String name) {
        return String.format("""
                SELECT ?river ?label (group_concat(?tributaryLabel;separator="/") as ?tributaries) ?image
                WHERE {
                  ?river wdt:P31 wd:Q4022;
                         wdt:P17 wd:Q159;
                         rdfs:label ?label;
                         wdt:P974 ?tributary.
                  FILTER (STRSTARTS(?label, "%s")).
                  OPTIONAL {
                    ?river wdt:P974 ?tributary.
                    ?river wdt:P18 ?image.
                  }
                  SERVICE wikibase:label {
                    bd:serviceParam wikibase:language "ru,en".
                    ?tributary rdfs:label ?tributaryLabel.
                  }
                } GROUP BY ?river ?label ?image having(count(?tributary) > 0)""", name);
    }

    @Override
    public String getType() {
        return queryType;
    }

}
