FROM openjdk:17
COPY . /usr/src/groogagle-jda
WORKDIR /usr/src/groogagle-jda
RUN mvn clean package
CMD ["java", "-jar", "target/groogagle-jda-1.0.0.jar"]