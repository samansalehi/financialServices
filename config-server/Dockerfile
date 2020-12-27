FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN mkdir /home/config-repo
RUN cd /home/config-repo
RUN apk add git
RUN git clone https://github.com/samansalehi/config-repo.git
ENTRYPOINT ["java","-jar","/app.jar"]