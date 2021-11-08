FROM openjdk:11
VOLUME /tmp
EXPOSE 8012
ADD ./target/creditcard-0.0.1-SNAPSHOT.jar creditcard.jar
ENTRYPOINT ["java","-jar","/creditcard.jar"]