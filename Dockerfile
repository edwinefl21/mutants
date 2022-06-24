FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} mutants-api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/mutants-api.jar"]

