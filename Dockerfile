FROM eclipse-temurin:17-alpine
COPY . /usr/src/groogagle-jda
WORKDIR /usr/src/groogagle-jda
RUN apk add maven
RUN mvn clean package
CMD ["java", "-jar", "target/groogagle-jda-1.0.0.jar"]