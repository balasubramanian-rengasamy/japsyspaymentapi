#!/usr/bin/env bash
gradle microBundle

java -jar build/libs/kotlinee-template-1.0-microbundle.jar \
--port 2499 \
--postbootcommandfile postboot.txt
--unpackdir /tmp/payara
