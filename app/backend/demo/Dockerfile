FROM openjdk:11
VOLUME /tmp
EXPOSE 9000
ADD target/demo-0.0.1-SNAPSHOT.war demo.war
ENTRYPOINT ["java","-jar","/demo.war"]
