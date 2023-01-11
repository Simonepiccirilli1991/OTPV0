FROM openjdk:17-jdk-alpine

COPY target/*.jar app.jar

EXPOSE 8088

LABEL name="OTPV0"

CMD ["java", "-jar", "app.jar"]