# Usa una imagen de OpenJDK como base
FROM openjdk:18

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR construido en el directorio de trabajo
COPY out/artifacts/demo_jar/demo.jar /app/
#para asegurar que se este copiando el archivo MANIFEST
COPY src/main/resources /app/src/main/resources

# Expone el puerto en el que tu aplicación Spring Boot está escuchando
EXPOSE 8084

# Comando para ejecutar tu aplicación Spring Boot
CMD ["java", "-jar", "demo.jar"]