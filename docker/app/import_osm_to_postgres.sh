#!/bin/bash

wget ${HOST_INFO}

imposm import -config config.json -mapping mapping.json -read ${RESULT_INFO_FILE} -overwritecache -write -diff