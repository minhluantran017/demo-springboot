FROM tomcat:8
LABEL maintainer=minhluantran017@gmail.com class=demo
ADD artifacts/demo-springboot.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD  ["catalina.sh","run"]
HEALTHCHECK CMD curl -f http://localhost:8080 || exit 1