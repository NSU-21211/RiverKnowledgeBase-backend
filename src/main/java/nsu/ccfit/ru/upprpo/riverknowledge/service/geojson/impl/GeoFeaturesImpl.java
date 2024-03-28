package nsu.ccfit.ru.upprpo.riverknowledge.service.geojson.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.Inflow;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.geojson.data.Feature;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.geojson.data.FeatureProperty;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.geojson.data.GeoJsonData;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.geojson.result.DistancesFromRiverMouth;
import nsu.ccfit.ru.upprpo.riverknowledge.model.entity.geojson.result.GeoServiceResult;
import nsu.ccfit.ru.upprpo.riverknowledge.service.geojson.GeoFeaturesService;
import nsu.ccfit.ru.upprpo.riverknowledge.util.JTSUtil;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Primary
@Slf4j
public class GeoFeaturesImpl implements GeoFeaturesService {
    private static final double EARTH_RADIUS_KM = 6378.14;

    @Override
    public GeoServiceResult getRiverInfoFromGeoJson(String sourceRiver) {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassLoader geoServiceClassLoader = this.getClass().getClassLoader();

        GeoJsonData geoJsonData;
        try {
            String fileName = "examples/ob_with_inflows_river.geojson"; // FIXME
            URL filePathURL = geoServiceClassLoader.getResource(fileName);
            Objects.requireNonNull(filePathURL);
            String jsonData = Files.readString(Path.of(filePathURL.getPath()));

            TypeReference<GeoJsonData> geoJsonDataTypeReference = new TypeReference<>() {
            };
            geoJsonData = objectMapper.readValue(jsonData, geoJsonDataTypeReference);
        } catch (IOException exception) {
            log.error("Cannot read geojson file: {}", exception.getMessage());
            throw new UncheckedIOException(exception);
        }

        Feature sourceRiverFeature = findFeatureForSourceRiver(geoJsonData.getFeatures(), sourceRiver)
                .orElseThrow(() -> new IllegalArgumentException("Source river not found"));

        List<Inflow> inflows = getInflows(geoJsonData.getFeatures(), sourceRiverFeature);
        double linearRiverLengthKm = calculateLinearRiverLength(sourceRiverFeature);
        List<DistancesFromRiverMouth> distances = calculateDistancesSourceRiver(sourceRiverFeature, geoJsonData.getFeatures());
        return new GeoServiceResult(inflows.size(), linearRiverLengthKm, distances);
    }

    /**
     * Note: last coordinate from geojson is the first position of river
     *
     * @param riverFeature
     * @return Last coordinate of river
     */
    private Coordinate getLastCoordinate(Feature riverFeature) {
        Geometry riverGeometry = JTSUtil.createJTSGeometryFromFeatureGeometry(riverFeature.getFeatureGeometry());
        Coordinate lastCoordinate;
        if (riverGeometry instanceof LineString lineString) {
            lastCoordinate = lineString.getCoordinateN(lineString.getNumPoints() - 1);
            return lastCoordinate;
        } else if (riverGeometry instanceof MultiLineString multiLineString) {
            int numGeometries = multiLineString.getNumGeometries();
            if (numGeometries > 0) {
                LineString lastLineString = (LineString) multiLineString.getGeometryN(numGeometries - 1);
                lastCoordinate = lastLineString.getCoordinateN(lastLineString.getNumPoints() - 1);
                return lastCoordinate;
            }
        }
        throw new IllegalArgumentException("Unsupported geometry type");
    }

    private double convertDegreesIntoKm(double degrees) {
        return degrees * (Math.PI / 180) * EARTH_RADIUS_KM;
    }

    public double calculateLinearRiverLength(Feature riverFeature) {
        double maxDistance = Double.NEGATIVE_INFINITY;
        Geometry riverGeometry = JTSUtil.createJTSGeometryFromFeatureGeometry(riverFeature.getFeatureGeometry());
        if (riverGeometry instanceof LineString lineString) {
            maxDistance = getMaxDistance(maxDistance, lineString);
        } else if (riverGeometry instanceof MultiLineString multiLineString) {
            for (int i = 0; i < multiLineString.getNumGeometries(); ++i) {
                LineString lineString = (LineString) multiLineString.getGeometryN(i);
                maxDistance = getMaxDistance(maxDistance, lineString);
            }
        }
        return convertDegreesIntoKm(maxDistance);
    }

    private double getMaxDistance(double maxDistance, LineString lineString) {
        Coordinate[] coordinates = lineString.getCoordinates();
        for (int i = 0; i < coordinates.length - 1; ++i) {
            for (int j = i + 1; j < coordinates.length; ++j) {
                double distance = coordinates[i].distance(coordinates[j]);
                if (distance > maxDistance) {
                    maxDistance = distance;
                }
            }
        }
        return maxDistance;
    }

    private Optional<Feature> findFeatureForSourceRiver(List<Feature> geoFeatures, String sourceRiver) {
        for (Feature feature : geoFeatures) {
            if (feature.getFeatureProperty().getRiverName().equalsIgnoreCase(sourceRiver)) {
                return Optional.of(feature);
            }
        }
        return Optional.empty();
    }

    private List<Inflow> getInflows(List<Feature> geoFeatures, Feature sourceRiverFeature) {
        String sourceRiverName = sourceRiverFeature.getFeatureProperty().getRiverName();
        List<Inflow> inflows = new ArrayList<>();
        for (Feature feature : geoFeatures) {
            FeatureProperty featureProperty = feature.getFeatureProperty();
            if (sourceRiverName.equalsIgnoreCase(featureProperty.getDestination())) {
                inflows.add(new Inflow(featureProperty.getRiverName()));
            }
        }
        return inflows;
    }

    /**
     * Note: this method calculates linear distances for every inflow
     *
     * @param sourceRiverFeature
     * @param geoFeatures
     * @return List of special parameters which presents distances from source river to inflow
     */
    private List<DistancesFromRiverMouth> calculateDistancesSourceRiver(Feature sourceRiverFeature, List<Feature> geoFeatures) {
        List<DistancesFromRiverMouth> distancesFromRiverMouthList = new ArrayList<>();
        for (Feature inflowFeature : geoFeatures) {
            if (!inflowFeature.equals(sourceRiverFeature)) {
                Coordinate sourceRiverLastCoordinate = getLastCoordinate(sourceRiverFeature);
                Coordinate inflowLastCoordinate = getLastCoordinate(inflowFeature);
                double distanceInKm = convertDegreesIntoKm(sourceRiverLastCoordinate.distance(inflowLastCoordinate));
                distancesFromRiverMouthList.add(new DistancesFromRiverMouth(
                        sourceRiverFeature.getFeatureProperty().getRiverName(),
                        inflowFeature.getFeatureProperty().getRiverName(),
                        distanceInKm)
                );
            }
        }
        return distancesFromRiverMouthList;
    }
}
