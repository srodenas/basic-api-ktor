#Utilizamos la imagen de Grandle con JDK 21 (lo tenemos igual en settings/grandle
FROM gradle:8-jdk21 AS build

#Definimos el directorio de trabajo dentro del contenedor y es app, para que contenga los ficheros de la api
#    Habrá que copiar todo lo que tenga el proyecto local a la carpeta /app del contenedor.
#    es como si al nombre de nuestro proyecto, le cambiamos el nombre app y lo dejamos en el contenedor.
WORKDIR /app

#copiamos todos los ficheros de nuestro proyecto local, dentro de app en el contenedor
#   Se copiará tanto la carpeta src, como build, como grandle, backend, upload, etc.
COPY . .

#Elimina cualquier compilación y construye el proyecto dentro de build. Esto lo hacemos, porque ya
#   tendremos una compilación hecha (que es la que hemos probado ejecutado en nuestra máquina local)

#installDist es la tarea de Grandle, que coge el src y lo compila, dejando un paquete ejecutable,
#  llamado srodenas-sample-employee2 en build/install/srodenas-sample-employee2/bin/ y
#  en /build/install/srodenas-sample-employee2/lib, los jar de las dependencias que hay definidas en el grandle.
#  por tanto, nos queda el ejecutable algo como /app/build/install/srodenas-sample-employee2/bin/srodenas-sample-employee2
RUN gradle clean installDist

#Crea la imagen final utilizando una version ligera.
FROM openjdk:21-jdk-slim

#Definimos el directorio app, donde se ejecutará la aplicación.
WORKDIR /app

#Copiamos la aplicación compilada desde build a la imagen.
#Solo copiamos los ficheros necesarios, tras la compilación generada en srodenas-sample-employee2
#   que incluye tanto la carpeta bin/srodenas-sample-employee2  como la carpeta lib/ con los jar de las dependencias.
COPY --from=build /app/build/install/srodenas-sample-employee2 /app

#Nuestra aplicación correrá en el 8081
EXPOSE 8081

#Lo que copiamos en app, fue  /bin y /lib, por tanto le decimos que al arrancar el servidor, acceda
#   a /app/bin/ y ejecute el binario srodenas-sample-employee2
#   cmd define el comando por defecto, a ejecutar cuando arranque el contenedor.
CMD ["./bin/srodenas-sample-employee2"]