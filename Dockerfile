#docker build -t cloud-alarm-server-renewal:v0.0.4-rc1 .
#docker tag cloud-alarm-server-renewal:v0.0.4-rc1 hencyyeo/cloud-alarm-server-renewal:v0.0.4-rc1
#docker push hencyyeo/cloud-alarm-server-renewal:v0.0.4-rc1
#docker run -itd --name cloud-backend-v2 -p 8081:8081 --network mariadb_default cloud-alarm-server-renewal:v0.0.2

FROM openjdk:11-jdk as builder
COPY . .
RUN ./gradlew bootjar

FROM openjdk:11-jdk
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY --from=builder build/libs/*.jar /usr/src/app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod","-Dfile.encoding=UTF-8", "/usr/src/app.jar"]
