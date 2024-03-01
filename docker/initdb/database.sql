DO
$$
    BEGIN
        IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'backend') THEN
            CREATE DATABASE backend;
        END IF;
    END
$$;

CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE points
(
    osm_id   BIGINT PRIMARY KEY,
    geometry GEOMETRY(Point, 4326)
);

CREATE INDEX idx_points_geom ON points USING GIST (geometry);

CREATE TABLE lines
(
    osm_id   BIGINT PRIMARY KEY,
    geometry GEOMETRY(LineString, 4326)
);

CREATE INDEX idx_lines_geom ON lines USING GIST (geometry);