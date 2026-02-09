#!/bin/bash
set -e

IMAGE_NAME="img-srodenas-api-employee-ktor:v2"
CONTAINER_NAME="api-employee-ktor-v2"
NETWORK_NAME="backend_ktor-network"
PROJECT_DIR="/home/santi/docker/api-employee"

echo "➡️  Parando contenedor si existe..."
docker stop $CONTAINER_NAME 2>/dev/null || true

echo "➡️  Eliminando contenedor si existe..."
docker rm $CONTAINER_NAME 2>/dev/null || true

echo "➡️  Eliminando imagen antigua si existe..."
docker rmi $IMAGE_NAME 2>/dev/null || true

echo "➡️  Construyendo nueva imagen..."
cd $PROJECT_DIR
docker build -t $IMAGE_NAME .

echo "➡️  Lanzando contenedor..."
docker run -d \
  --name $CONTAINER_NAME \
  --network $NETWORK_NAME \
  -p 8082:8081 \
  --restart unless-stopped \
  $IMAGE_NAME

echo "✅ Despliegue v2 completado correctamente"
