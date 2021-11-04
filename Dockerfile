FROM adoptopenjdk/openjdk11:alpine-jre@sha256:8ed5d2fbc5e382c5c7030fff620d4c4095730ac2c1c735b3bcd5a7bf745194e6

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

USER appuser

ARG JAR_FILE
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000", "-Dspring.profiles.active=docker","-jar","/app.jar"]

EXPOSE 80