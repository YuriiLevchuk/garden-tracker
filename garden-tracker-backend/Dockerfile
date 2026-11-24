FROM amazoncorretto:17-jdk AS build
WORKDIR /app
RUN yum install -y tar gzip && yum clean all
COPY . .
RUN ./mvnw clean package -DskipTests

FROM amazoncorretto:17-headless
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar
EXPOSE 8080
CMD ["java", "-jar", "./app.jar"]