#docker build -t cloud-alarm-server:v0.0.1 .
#docker tag cloud-alarm-server:v0.0.1 hencyyeo/cloud-alarm-server:v0.0.1
#docker push hencyyeo/cloud-alarm-server:v0.0.1

FROM openjdk:11-jdk as builder
COPY . .
RUN ./gradlew bootjar

FROM openjdk:11-jdk
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY --from=builder build/libs/*.jar /usr/src/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dfile.encoding=UTF-8", "/usr/src/app.jar"]
