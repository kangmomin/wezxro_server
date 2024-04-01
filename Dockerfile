FROM openjdk:18-alpine
ARG JAR_FILE=build/libs/wezxro_server-0.0.1-SNAPSHOT.jar
ENV LANG C.UTF-8
COPY ${JAR_FILE} app.jar
CMD ["java", "-jar", "/app.jar"]