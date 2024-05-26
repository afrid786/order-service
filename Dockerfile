FROM eclipse-temurin:21-jdk-alpine
WORKDIR /opt
COPY target/order-service.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar