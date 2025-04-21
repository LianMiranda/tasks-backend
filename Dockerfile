FROM tomcat:9.0.102-jdk21-temurin-noble

ARG WAR_FILE=
ARG CONTEXT

# Criar um usuário não-root
RUN groupadd -r tomcat && useradd -r -g tomcat tomcat

# Copiar o arquivo WAR e ajustar as permissões
COPY ${WAR_FILE} /usr/local/tomcat/webapps/${CONTEXT}.war
RUN chown -R tomcat:tomcat /usr/local/tomcat

# Mudar para o usuário não-root
USER tomcat
