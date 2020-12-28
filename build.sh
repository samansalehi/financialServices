#!/usr/bin/env bash

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

cd ${DIR}/accountservice

./mvnw clean install -DskipTests
docker build -t account-service:latest .

cd ${DIR}/config-server

./mvnw clean install -DskipTests
docker build -t config-server:latest .

cd ${DIR}/eureka-server

./mvnw clean install -DskipTests
docker build -t eureka-server:latest .

cd ${DIR}/transactionservice

./mvnw clean install -DskipTests
docker build -t transaction-service:latest .
