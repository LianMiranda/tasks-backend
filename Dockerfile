FROM tomcat:9.0.102-jdk21-temurin-noble

ARG WAR_FILE=
ARG CONTEXT

COPY ${WAR_FILE} /usr/local/tomcat/webapps/${CONTEXT}.war
