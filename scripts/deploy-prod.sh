#!/bin/bash
# VPS本番再デプロイ（/home/nakamura/project で実行）
set -euo pipefail

PROJECT_DIR="${PROJECT_DIR:-/home/nakamura/project}"
COMPOSE_FILE="${COMPOSE_FILE:-docker-compose-prod.yml}"

cd "$PROJECT_DIR"

echo "==> git pull"
git pull origin main

echo "==> Maven build (ROOT.war)"
mvn clean package -DskipTests

echo "==> Docker rebuild & restart (DBデータは mysql_data ボリュームを維持)"
docker compose -f "$COMPOSE_FILE" build app
docker compose -f "$COMPOSE_FILE" up -d

echo "==> status"
docker compose -f "$COMPOSE_FILE" ps

echo "Done. URL: http://160.251.205.21:9146/"
