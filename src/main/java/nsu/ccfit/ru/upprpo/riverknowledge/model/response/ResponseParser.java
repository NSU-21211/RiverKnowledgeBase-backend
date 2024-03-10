package nsu.ccfit.ru.upprpo.riverknowledge.model.response;

import com.bordercloud.sparql.SparqlResultModel;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.RiverEntity;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ResponseParser {
    public List<RiverEntity> parseSparqlResult(SparqlResultModel resultModel) {
        List<RiverEntity> rivers = new ArrayList<>();

        for (int i = 0; i < 15; ++i) {
            RiverEntity river = new RiverEntity();

            river.setRiver(URI.create(String.valueOf(resultModel.getRows().get(i).get("river"))));
            river.setLabel(String.valueOf(resultModel.getRows().get(i).get("label")));
            river.setCoordinateLocation(String.valueOf(resultModel.getRows().get(i).get("coordinateLocation")));
            river.setLength((Integer.valueOf(String.valueOf(resultModel.getRows().get(i).get("length")))));
            river.setTributary(URI.create(String.valueOf(resultModel.getRows().get(i).get("tributary"))));
            river.setImage(URI.create(String.valueOf(resultModel.getRows().get(i).get("image"))));

            rivers.add(river);
        }

        return rivers;
    }

}
