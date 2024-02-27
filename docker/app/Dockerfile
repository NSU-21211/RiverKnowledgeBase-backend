FROM openjdk:8-jdk

ENV OSMOSIS_VERSION=0.48.3

# Обновляем список пакетов и устанавливаем необходимые инструменты
RUN apt-get update && \
    apt-get install -y \
    wget \
    unzip \
    postgresql-client \
    osm2pgsql

# Загружаем Osmosis и распаковываем его
RUN wget https://github.com/openstreetmap/osmosis/releases/download/$OSMOSIS_VERSION/osmosis-$OSMOSIS_VERSION.tgz && \
    mkdir -p /opt/osmosis && \
    tar -xvzf osmosis-$OSMOSIS_VERSION.tgz -C /opt/osmosis && \
    rm osmosis-$OSMOSIS_VERSION.tgz

# Добавляем путь к Osmosis в переменную PATH
ENV PATH="/opt/osmosis/bin:${PATH}"

# Устанавливаем рабочую директорию
WORKDIR /workspace

# Копируем скрипт для импорта данных OSM в PostgreSQL
COPY import_osm_to_postgres.sh /workspace/import_osm_to_postgres.sh
RUN chmod +x /workspace/import_osm_to_postgres.sh

# Запускаем скрипт по умолчанию
CMD ["./import_osm_to_postgres.sh"]
