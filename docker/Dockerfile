FROM maven AS build
WORKDIR /dist/src/app
COPY . .
RUN mvn install -DskipTests

FROM openjdk:11 as run
RUN sh -c 'echo "deb http://ftp.de.debian.org/debian stretch contrib" >> /etc/apt/sources.list' \ 
    && apt-get update \
    && apt-get install -y netcat nano
COPY --from=build /dist/src/app/target .
COPY /docker/entrypoint.sh .
ENTRYPOINT [ "/entrypoint.sh" ]
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "learning-platform-backend-0.0.1-SNAPSHOT.jar"]