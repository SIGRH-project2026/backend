FROM tomcat:10.1.11-jdk21-temurin
RUN sed -i 's/port="8080"/port="9083"/' /usr/local/tomcat/conf/server.xml
ADD target/*.war /usr/local/tomcat/webapps/
EXPOSE 9083
CMD ["catalina.sh", "run"]


