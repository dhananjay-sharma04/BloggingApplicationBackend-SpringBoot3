FROM amazoncorretto:17.0.7
ARG JAR_FILE=build/libs/blogapplication-0.0.1-SNAPSHOT-plain.jar
COPY ${JAR_FILE} blogapplication.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "/blogapplication.jar"]