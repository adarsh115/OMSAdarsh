FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/oms-order-service.jar app.jar
EXPOSE 8182
ENTRYPOINT ["java", "-jar", "app.jar"]
