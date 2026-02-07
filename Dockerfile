#La construcción del contenedor, se llevará en dos etapas.
#Primera etapa, es la preparación del grandle con la versión que necesitamos. Por defecto, la versión
#gradle:8-jdk19, que era la que utilizamos en la primera versión de este dockerfile, da error porque esta api
#necesita minimo una 8.3. Por tanto, hay que instalar a pelo la versión de gradle, que en mi caso es la 8.4


#Una vez descargada e instalada la versión de gradle, debemos de copiar los ficheros de configuración
#del gradle, descargar las dependencias y compilar el código fuente.


#Segunda etapa, es copiar todo lo hecho en la primera etapa, a una nueva imagen más liviana
#donde tenga, sólo lo necesario para ser ejecutado.
#Lo hacemos en dos etapas. Primera etapa, con una imagen para la compilación y una segunda imagen, que es la
#que necesitaremos la ejecución de nuestra API.

#Docker, descarta la primera etapa (primera imagen) y se queda con la segunda o última etapa(segunda imagen)
#-----------------------------------------------

#PRIMERA ETAPA.
#Necesitamos instalar a mano una versión de gradle 8.4
#Debemos identificar la etapa como build, para que en la primera etapa, copie los ficheros compilados desde la primera etapa.
#Imagen que funciona en mi raspi ARM/v7 de 32 bits.
FROM arm32v7/eclipse-temurin:17-jdk AS build

#Definimos el directorio app, donde se ejecutará la aplicación.
WORKDIR /app

#Necesitamos las dependencias de wget y unzip
#Liberamos todo lo innecesario del directorio temporal
RUN apt-get update && apt-get install -y wget unzip \
    && rm -rf /var/lib/apt/lists/*

#Necesitamos crearnos un usuario para la asignación de permisos a upload
RUN apt-get update && apt-get install -y --no-install-recommends passwd \
    && rm -rf /var/lib/apt/lists/* \
    && useradd -r -s /usr/sbin/nologin app


#Necesitamos gradle 8.4. Lo descargamos y lo instalamos.
# Descargar e instalar Gradle 8.4 manualmente
#Con wget, descargamos la distribución 8.4
#Lo descomprimimos de la carpeta temporal y lo  guarda en la carpeta opt.
#Ahora, necesitamos un enlace simbólico de la carpeta descomprimida a /usr/local/bin/gradle
#Nos cargamos todo lo que hay en la carpeta temporal. Lo descargado.
RUN wget https://services.gradle.org/distributions/gradle-8.4-bin.zip -O /tmp/gradle.zip \
    && unzip /tmp/gradle.zip -d /opt/ \
    && ln -s /opt/gradle-8.4/bin/gradle /usr/local/bin/gradle \
    && rm -rf /tmp/gradle.zip





#Copiamos los ficheros de configuración, para que se descargue todas las dependencias.
#Primero los scripts de configuración del Grandle
#build.gradle.kts es donde están las dependencias que necesitamos
#settings.gradle.kts define la configuración global del proyecto.
#    es donde tenemos el nombre del proyecto.
#gradlew es un fichero para linux/mac, que usa la versión del grandle-wrapper.properties para ejecutar gradle.
#   Si no lo encuentra, lo descarga.
#gradlew.bat es un fichero para windows, que usa la versión del grandle-wrapper.properties para ejecutar gradle
#   Si no lo encuentra, lo descarga.
# Pregunta. Si el fichero gradlew y .bat descarga la versión de gradle, porqué lo hemos hecho manual?
# Para que no tenga que descargarse en caché, cada vez que construimos el contenedor. punto.
COPY build.gradle.kts settings.gradle.kts gradlew gradlew.bat ./

#copiamos toda la carpeta gradle. No nos complicamos la vida. Por si luego nos falta cualquier otra cosa.
COPY gradle gradle/

#Damos los permisos de ejecución al script gradlew en el caso de linux/mac.
RUN chmod +x gradlew

#Vamos a verificar que se ha descargado, descomprimido e instalado correctamente.
#RUN ./gradlew --version

#descargamos dependencias, para que no lo haga en el proceso de compilación
RUN ./gradlew dependencies --no-daemon
#Por último, debemos de compilar nuestro código fuente.
#   installDist es la tarea de Grandle, que coge el src y lo compila, dejando un paquete ejecutable,
#   llamado srodenas-sample-employee2 en build/install/srodenas-sample-employee2/bin/ y
#  en /build/install/srodenas-sample-employee2/lib, los jar de las dependencias que hay definidas en el grandle.
#  por tanto, nos queda el ejecutable algo como /app/build/install/srodenas-sample-employee2/bin/srodenas-sample-employee2

#Copiamos el src. Lo copiamos después, para que no vuelva a repetir los pasos anteriores.
#Como son capas, si copiamos primero el src, se ejcutará dicha capa y después se descargará las dependencias
#en el siguiente paso, por tanto, en el caso de que volvamos a modificar e incluir otro copy, aunque sea un simple
#directorio, como ha cambiado, volverá también a realizar de nuevo el siguiente paso, que es descargar dependencias y no
#tirará de cache.

COPY upload upload

# Crear usuario y grupo 'app' en la primera etapa
#RUN groupadd --system app && useradd --system -g app app

RUN chown -R app:app upload && chmod -R 755 upload
#RUN  chmod -R 777 upload  #para que pueda subir las imagenes.
COPY backend backend
COPY src src

RUN ./gradlew clean installDist --no-daemon


#SEGUNDA ETAPA...... Creamos una nueva imagen limpia y copiamos lo necesario.
#FROM openjdk:19-jdk-slim
FROM arm32v7/eclipse-temurin:17-jre
WORKDIR /app

ARG APP_NAME=srodenas-sample-employee2
#Copiamos la aplicación compilada desde build a la imagen.
#Solo copiamos los ficheros necesarios, tras la compilación generada en srodenas-sample-employee2
#   que incluye tanto la carpeta bin/srodenas-sample-employee2  como la carpeta lib/ con los jar de las dependencias.
#De la imagen llamada build, copiamos lo que hemos compilado.
COPY --from=build /app/build/install/${APP_NAME}/ /app

#Creamos el usuario que se encargará de ejecutar la app.
RUN apt-get update && apt-get install -y --no-install-recommends passwd \
    && rm -rf /var/lib/apt/lists/* \
    && useradd -r -s /usr/sbin/nologin app

#Usar el usuario no root. Debe ejecutarse con el usuario app
USER app


#Nuestra aplicación correrá en el 8081
EXPOSE 8081

#Lo que copiamos en app, fue  /bin y /lib, por tanto le decimos que al arrancar el servidor, acceda
#   a /app/bin/ y ejecute el binario srodenas-sample-employee2
#   cmd define el comando por defecto, a ejecutar cuando arranque el contenedor.
CMD ["/app/bin/srodenas-sample-employee2"]

#docker build -t img-srodenas-api-employee-ktor:latest .
#docker run -p 8081:8081 --name api-employee-ktor img-srodenas-api-employee-ktor:latest
#docker logs -f api-employee-ktor
#docker stop api-employee-ktor

#docker logs -f api-employee-ktor
#docker rm api-employee-ktor
#docker rmi img-srodenas-api-employee-ktor   #borro la imagen
#docker-compose down -v    #para que borre también los volúmenes en el caso de que quiera volver a lanzarlo.
#docker-compose up -d  --build   #para levantarlos, pero que construya antes la de la api.
