FROM anapsix/alpine-java:8_jdk
MAINTAINER Jeff Klein "jeff@jeffklein.org"
ENV PROJECT nfllivescores
RUN sed -i -e 's/dl-cdn/dl-4/' /etc/apk/repositories && \
    apk add --no-cache \
        bash \
        git
WORKDIR /
RUN git clone https://github.com/jeffklein/$PROJECT.git
WORKDIR /nfllivescores
RUN chmod u+x ./gradlew && ./gradlew build -x test
WORKDIR /nfllivescores/build/libs
EXPOSE 8080
CMD ["java", "-jar", "./nfllivescores-springboot-0.1.0.jar"]
