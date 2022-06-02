FROM openjdk:11

COPY ./build/libs/orders-api-0.0.1-SNAPSHOT.jar orders-api-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "orders-api-0.0.1-SNAPSHOT.jar"]