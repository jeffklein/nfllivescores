FROM anapsix/alpine-java:8_jdk
MAINTAINER Jeff Klein "jeff@jeffklein.org"
RUN sed -i -e 's/dl-cdn/dl-4/' /etc/apk/repositories && \
    apk add --no-cache \
        bash \
        git
WORKDIR /
RUN git clone https://github.com/jeffklein/nfllivescores.git
WORKDIR /nfllivescores
RUN ["ls", "-al", "./gradlew"]
RUN ["./gradlew", "build", "-x", "test"]
WORKDIR /nfllivescores/build/libs
EXPOSE 8080
ENTRYPOINT ["java", "-version"]
