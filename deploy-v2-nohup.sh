#!/bin/bash
# deploy-v2-nohup.sh
# Script para construir y lanzar la API v2 en la Raspi
# Diseñado para ejecutarse en background con nohup
# Sin usar volumen, la carpeta /upload ya viene en la imagen

IMAGE_NAME="img-srodenas-api-employee-ktor:v2"
CONTAINER_NAME="api-employee-ktor-v2"
NETWORK_NAME="backend_ktor-network"
HOST_PORT=8082
CONTAINER_PORT=8081

echo "🚀 Iniciando deploy v2..."

# Parar y eliminar contenedor antiguo si existe
echo "🛑 Deteniendo contenedor antiguo si existe..."
docker rm -f $CONTAINER_NAME 2>/dev/null || true

# Eliminar imagen antigua
echo "🗑 Eliminando imagen antigua si existe..."
docker rmi $IMAGE_NAME 2>/dev/null || true

# Construir nueva imagen
echo "📦 Construyendo imagen Docker..."
docker build -t $IMAGE_NAME . || { echo "❌ Error en build"; exit 1; }

# Crear y lanzar contenedor en segundo plano, reinicio automático y red correcta
echo "📤 Lanzando contenedor..."
docker run -d \
  --name $CONTAINER_NAME \
  --network $NETWORK_NAME \
  -p $HOST_PORT:$CONTAINER_PORT \
  --restart unless-stopped \
  $IMAGE_NAME || { echo "❌ Error al crear contenedor"; exit 1; }

echo "✅ Deploy v2 completado. Contenedor corriendo en segundo plano."
echo "Para ver logs: docker logs -f $CONTAINER_NAME"
