# Ideally build should use CI tools like Jenkins
FROM maven:3-amazoncorretto-17 AS build

WORKDIR /build

# Cache dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline

# Skip test for docker build, should test before build
COPY . .
RUN mvn clean verify -DskipTests

####
FROM amazoncorretto:17

RUN yum install -y shadow-utils && useradd appuser
USER appuser

WORKDIR /app

COPY --from=build /build/target/demo-*.jar ./app.jar

ENTRYPOINT ["java", "-jar" , "./app.jar"]
