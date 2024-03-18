package nsu.ccfit.ru.upprpo.riverknowledge.model.response;

import com.bordercloud.sparql.SparqlResultModel;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class ResponseParser {

    public void parseRiverInfo(SparqlResultModel resultModel, Map<URI, RiverEntity> rivers) {
        for (int i = 0; i < resultModel.getRowCount(); ++i) {
            URI riverLink = URI.create(String.valueOf(resultModel.getRows().get(i).get("river")));
            String riverLabel = String.valueOf(resultModel.getRows().get(i).get("label"));

            if (rivers.containsKey(riverLink) && rivers.get(riverLink).getLabel().equals(riverLabel)) {
                RiverEntity river = rivers.get(riverLink);
                river.getOrigin().add(String.valueOf(resultModel.getRows().get(i).get("originLabel")));
                river.getAdministrativeTerritorial().add(String.valueOf(resultModel.getRows().get(i).get("locatedLabel")));
                river.getMouth().add(String.valueOf(resultModel.getRows().get(i).get("mouthLabel")));
            } else {
                RiverEntity river = new RiverEntity();
                river.setRiverLink(riverLink);
                river.setLabel(riverLabel);
                river.setLength(getIntegerValue(resultModel.getRows().get(i).get("length")));
                river.setWatershedArea(getIntegerValue(resultModel.getRows().get(i).get("watershed")));
                river.setImage(URI.create(String.valueOf(resultModel.getRows().get(i).get("image"))));
                river.getOrigin().add(String.valueOf(resultModel.getRows().get(i).get("originLabel")));
                river.getAdministrativeTerritorial().add(String.valueOf(resultModel.getRows().get(i).get("locatedLabel")));
                river.getMouth().add(String.valueOf(resultModel.getRows().get(i).get("mouthLabel")));
                river.setCountry(String.valueOf(resultModel.getRows().get(i).get("countryLabel")));

                rivers.put(riverLink, river);
            }
        }
    }

    public void parseRiverTributaries(SparqlResultModel resultModel, Map<URI, RiverEntity> rivers) {
        for (int i = 0; i < resultModel.getRowCount(); ++i) {
            URI riverLink = URI.create(String.valueOf(resultModel.getRows().get(i).get("river")));
            String riverLabel = String.valueOf(resultModel.getRows().get(i).get("label"));

            if (rivers.containsKey(riverLink) && rivers.get(riverLink).getLabel().equals(riverLabel)) {
                RiverEntity river = rivers.get(riverLink);

                if (river.getTributaries().isEmpty()) {
                    String resultTributaries = String.valueOf(resultModel.getRows().get(i).get("tributaries"));
                    String[] tributaries = resultTributaries.split("/");
                    river.setTributaries(new HashSet<>(List.of(tributaries)));
                }
            }
        }
    }

    private Integer getIntegerValue(Object value) {
        return value != null ? Integer.valueOf(String.valueOf(value)) : null;
    }
}
