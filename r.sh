#!/usr/bin/env bash
gradle build

java -jar  ~/projects/payara-micro-5.201.jar \
--deploy build/libs/japsysapi-1.0.war \
--port 2499 \
--unpackdir /tmp/payara \
--nohostaware \
--nocluster

