# -------- Build Stage --------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# -------- Run Stage --------
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
COPY apminsight-javaagent ./apminsight-javaagent

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java \
  -javaagent:apminsight-javaagent/apminsight-javaagent.jar \
  -Dapminsight.license.key=$SITE24_LICENSE \
  -Dserver.port=$PORT \
  -jar app.jar"]
