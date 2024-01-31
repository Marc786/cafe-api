# Cache stage
FROM maven:3-openjdk-18-slim AS cache

RUN adduser --disabled-password --gecos "" cache
WORKDIR /home/cache
USER cache

COPY --chown=cache:cache pom.xml /home/cache/pom.xml
COPY --chown=cache:cache cafe-api/pom.xml /home/cache/cafe-api/pom.xml

RUN mvn test

# Run stage
FROM maven:3-openjdk-18-slim AS runner

RUN adduser --disabled-password --gecos "" maven
WORKDIR /home/maven
USER maven

COPY --chown=maven:maven --from=cache /home/cache/.m2/ /home/maven/.m2/

COPY --chown=maven:maven . /home/maven

RUN mvn clean install
ENTRYPOINT ["mvn", "exec:java", "-pl", "cafe-api"]
