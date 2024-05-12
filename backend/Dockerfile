# Utilizamos una imagen base con Java 17 preinstalado
FROM openjdk:17-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el archivo JAR generado por Maven a la imagen del contenedor
COPY target/swapi-0.0.1-SNAPSHOT.jar /app/swapi.jar

# Comando para ejecutar la aplicación cuando el contenedor se inicie
CMD ["java", "-jar", "swapi.jar"]