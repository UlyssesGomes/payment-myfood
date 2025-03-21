FROM openjdk:21
# RUN addgroup -S spring && adduser -S spring -G spring
# USER spring:spring

ENV DB_USER=admin
ENV DB_PASSWORD=master

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
