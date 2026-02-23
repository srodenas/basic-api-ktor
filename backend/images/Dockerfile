# =========================
# ETAPA DE BUILD
# =========================
FROM --platform=$BUILDPLATFORM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Instalar dependencias necesarias
RUN apt-get update && apt-get install -y wget unzip \
    && rm -rf /var/lib/apt/lists/*

# Crear usuario 'app' para uploads
RUN apt-get install -y --no-install-recommends passwd \
    && rm -rf /var/lib/apt/lists/* \
    && useradd -r -s /usr/sbin/nologin app

# Instalar Gradle 8.4 manualmente
RUN wget https://services.gradle.org/distributions/gradle-8.4-bin.zip -O /tmp/gradle.zip \
    && unzip /tmp/gradle.zip -d /opt/ \
    && ln -s /opt/gradle-8.4/bin/gradle /usr/local/bin/gradle \
    && rm -rf /tmp/gradle.zip

# Copiar configuración de Gradle
COPY build.gradle.kts settings.gradle.kts gradlew gradlew.bat ./
COPY gradle/ gradle/
RUN chmod +x gradlew

# Descargar dependencias
RUN ./gradlew dependencies --no-daemon

# Copiar código fuente
COPY upload upload
COPY backend backend
COPY src src

# Dar permisos
RUN chown -R app:app upload && chmod -R 755 upload

# Compilar aplicación
RUN ./gradlew clean installDist --no-daemon

# =========================
# ETAPA FINAL
# =========================
FROM --platform=$BUILDPLATFORM eclipse-temurin:17-jre AS runtime
WORKDIR /app

ARG APP_NAME=srodenas-sample-employee2
COPY --from=build /app/build/install/${APP_NAME}/ /app

# Crear usuario no root
RUN apt-get update && apt-get install -y --no-install-recommends passwd \
    && rm -rf /var/lib/apt/lists/* \
    && useradd -r -s /usr/sbin/nologin app
USER app

EXPOSE 8081

# Ejecutar la API
CMD ["/app/bin/srodenas-sample-employee2"]