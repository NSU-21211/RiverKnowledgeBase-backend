#!/bin/bash

# Скачиваем небольшой файл .osm.pbf
wget http://download.geofabrik.de/europe/monaco-latest.osm.pbf

# Выполняем запрос к osmosis для фильтрации данных
osmosis \
    --read-pbf file="monaco-latest.osm.pbf" \
    --bounding-box top=43.7512 left=7.4061 bottom=43.7235 right=7.4395 \
    --write-pgsql host="river_knowledge_postgres" database="backend" user="postgres" password="postgres"

echo "Импорт в базу данных успешно завершен."
