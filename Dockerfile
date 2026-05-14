FROM tomcat:10.1

RUN rm -rf /usr/local/tomcat/webapps/*

COPY target/myportfolio-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080