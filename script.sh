#!/bin/bash

mvn clean package -DskipTests

docker build -t andersonreiner/lib-api:latest .

docker push andersonreiner/lib-api:latest

cd ~/production-environment

./update.sh