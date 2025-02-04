FROM openjdk:11

RUN apt update && apt upgrade -y && rm -rf /var/lib/apt/lists/*

RUN mkdir server
RUN mkdir plugin
COPY ./server/target/cqf-ruler-server-*.war server/ROOT.war
EXPOSE 8080
CMD ["java", "-cp", "server/ROOT.war", "-Dloader.path=WEB-INF/classes,WEB-INF/lib,WEB-INF/lib-provided,plugin", "org.springframework.boot.loader.PropertiesLauncher"]
