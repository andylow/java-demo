# JAVA DEMO

## Pre-requisite
There are 2 ways to build the projects,
make sure the required pre-requisites are installed based on chosen method:

1. Using Docker
    - Docker v20+
    - Docker Compose v2 (Optional)
2. Using Maven
    - JDK v17
    - Maven v3.9+ (Optional, wrapper provided)

## Database setup
For demo purpose, H2 in-memory database is being used as the default database in `application.properties`
```properties
spring.datasource.url=jdbc:h2:mem:localdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=db
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```
With above configuration, **there is no setup needed**.
H2 in-memory will started-up and a reduced size `ukpostcodes.csv` will be loaded using `Liquibase` together with the application launch.

If you want to use a proper DB (For e.g. PostgreSQL), you can update the 
spring datasource properties accordingly.

## How to build

### Using Docker

#### 1. Docker Compose way:
If you have `Docker Compose` installed, just run:
```
docker compose build
```
from the project directory, the `Dockerfile` it will build java and package it into a docker image. The docker image will use the folder name as the image name.

#### 2. Docker way:
To build using plain docker command:
```
docker build . -t java-app:latest
```

### Using maven
Use either `mvn` (System installed maven) or `./mvnw` (Maven wrapper) to run following command to build:
```
mvn clean verify
```
After successfully execute, you should found a `.jar` file in the `target` folder.


## How to run

### Using Docker

#### 1. Using Docker Compose
```
docker compose up
```

#### 2. Using Docker
```
docker run -p 8080:8080 java-app:latest
```

Above two command will bring up the container to run image that built from previous step.

### Use Java
```
java -jar ./target/demo-1.0.jar
```

Once you see the console is printing:
```
Started DemoApplication in 3.803 seconds (process running for 6.937)
```
means the application has started correctly.

# Getting Started
Once application server is running, 
you can access `/swagger-ui.html` to try out the API using OpenAPI docs.
For e.g.: http://localhost:8080/swagger-ui.html