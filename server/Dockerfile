FROM openjdk:8-jdk-alpine
WORKDIR /opt
COPY ./target/*.jar /opt/app.jar
EXPOSE 8091
CMD ["java","-jar","app.jar"]