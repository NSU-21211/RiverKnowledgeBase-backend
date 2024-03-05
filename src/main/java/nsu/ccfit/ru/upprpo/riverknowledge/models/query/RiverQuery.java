package nsu.ccfit.ru.upprpo.riverknowledge.models.query;

public class RiverQuery {
    private RiverQuery() {
        throw new IllegalStateException("Utility class");
    }

    public static String getRiverQuery(String name) {
        return "SELECT ?river ?label ?coordinateLocation ?length ?tributary ?image\n" +
                "WHERE\n" +
                "{\n" +
                "  ?river wdt:P31 wd:Q4022;\n" +
                "          wdt:P17 wd:Q159;\n" +
                "          rdfs:label ?label.\n" +
                "   FILTER (STRSTARTS(?label, \"" + name + "\")).\n" +
                "   OPTIONAL {\n" +
                "   ?river wdt:P625 ?coordinateLocation.\n" +
                "   ?river wdt:P2043 ?length.\n" +
                "   ?river wdt:P974 ?tributary.\n" +
                "   ?river wdt:P18 ?image.\n" +
                "   }\n" +
                "   SERVICE wikibase:label { bd:serviceParam wikibase:language \"ru,en\". }\n" +
                "}";
    }
}
