FROM openmaptiles/import-osm:4.0.0

WORKDIR /workspace

COPY ../import_osm_to_postgres.sh /workspace/import_osm_to_postgres.sh
COPY ../config.json /workspace/config.json
COPY ../mapping.json /workspace/mapping.json

RUN chmod +x /workspace/import_osm_to_postgres.sh

CMD ["./import_osm_to_postgres.sh"]