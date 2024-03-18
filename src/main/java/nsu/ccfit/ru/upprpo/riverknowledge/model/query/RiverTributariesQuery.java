package nsu.ccfit.ru.upprpo.riverknowledge.model.query;

public class RiverTributariesQuery {
    private RiverTributariesQuery() {
        throw new IllegalStateException("Utility class");
    }

    public static String getRiverTributariesQuery(String name) {
        return "SELECT ?river ?label (group_concat(?tributaryLabel;separator=\"/\") as ?tributaries)\n" +
                "WHERE {\n" +
                "  ?river wdt:P31 wd:Q4022;\n" +
                "         wdt:P17 wd:Q159;\n" +
                "         rdfs:label ?label;\n" +
                "         wdt:P974 ?tributary.\n" +
                "  FILTER (STRSTARTS(?label, \"" + name + "\")).\n" +
                "  OPTIONAL {\n" +
                "    ?river wdt:P974 ?tributary.\n" +
                "  }\n" +
                "  SERVICE wikibase:label {\n" +
                "    bd:serviceParam wikibase:language \"ru,en\".\n" +
                "    ?tributary rdfs:label ?tributaryLabel.\n" +
                "  }\n" +
                "} GROUP BY ?river ?label having(count(?tributary) > 1)";
    }
}
