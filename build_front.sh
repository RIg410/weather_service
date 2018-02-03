#!/usr/bin/env bash
DIR=`pwd`
cd ${DIR}/front/weather_front && npm run build
rm -r ${DIR}/src/main/resources/static/*
mkdir ${DIR}/src/main/resources/static
mv ${DIR}/front/weather_front/build/* ${DIR}/src/main/resources/static/