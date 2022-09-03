FROM openjdk:8-jdk-alpine
WORKDIR /opt
COPY target/*.jar /opt
COPY start.sh /opt
EXPOSE 8091
CMD ["sh", "-c", "start.sh"]