package nsu.ccfit.ru.upprpo.riverknowledge.util;

import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.geojson.data.FeatureGeometry;
import org.locationtech.jts.geom.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class JTSUtil {
    private static final int ID_FOR_WGS_84 = 4326;

    private JTSUtil() {
        throw new IllegalStateException("Instantiation of util class");
    }

    /**
     * Note: GeoJSON's order (longitude, latitude) must be converted to (latitude, longitude)
     *
     * @param featureGeometry
     * @return Geometry for WGS-84 system
     */
    public static Geometry createJTSGeometryFromFeatureGeometry(FeatureGeometry featureGeometry) {
        List<?> coordinates = featureGeometry.getGeometryCoordinates();
        if (Objects.isNull(coordinates) || coordinates.isEmpty()) {
            throw new IllegalArgumentException("No coordinates provided");
        }

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), ID_FOR_WGS_84);
        String geometryType = featureGeometry.getGeometryType();
        if ("Point".equalsIgnoreCase(geometryType)) {
            @SuppressWarnings(value = "unchecked")
            List<Double> pointCoordinates = (List<Double>) coordinates.get(0);
            double longitude = pointCoordinates.get(0);
            double latitude = pointCoordinates.get(1);
            Coordinate coordinate = new Coordinate(latitude, longitude);
            return geometryFactory.createPoint(coordinate);
        } else if ("LineString".equalsIgnoreCase(geometryType)) {
            Coordinate[] lineCoordinates = new Coordinate[coordinates.size()];
            for (int i = 0; i < coordinates.size(); i++) {
                List<?> coordinate = (List<?>) coordinates.get(i);
                double longitude = ((Number) coordinate.get(0)).doubleValue();
                double latitude = ((Number) coordinate.get(1)).doubleValue();
                lineCoordinates[i] = new Coordinate(latitude, longitude);
            }
            return geometryFactory.createLineString(lineCoordinates);
        } else if ("MultiLineString".equalsIgnoreCase(geometryType)) {
            List<LineString> lineStrings = new ArrayList<>();
            for (Object rawCoordinates : coordinates) {
                @SuppressWarnings(value = "unchecked")
                List<List<Double>> lineCoordinates = (List<List<Double>>) rawCoordinates;
                Coordinate[] lineCoordinatesArray = new Coordinate[lineCoordinates.size()];
                for (int i = 0; i < lineCoordinates.size(); i++) {
                    List<Double> coordinate = lineCoordinates.get(i);
                    double longitude = coordinate.get(0);
                    double latitude = coordinate.get(1);
                    lineCoordinatesArray[i] = new Coordinate(latitude, longitude);
                }
                LineString lineString = geometryFactory.createLineString(lineCoordinatesArray);
                lineStrings.add(lineString);
            }
            return geometryFactory.createMultiLineString(lineStrings.toArray(new LineString[0]));
        }
        throw new IllegalArgumentException("Unsupported geometry type");
    }
}
