FROM anapsix/alpine-java:8_jdk
MAINTAINER Jeff Klein "jeff@jeffklein.org"
RUN sed -i -e 's/dl-cdn/dl-4/' /etc/apk/repositories && \
    apk add --no-cache \
        bash \
        git \
        openssh
RUN git clone git@github.com:jeffklein/nfllivescores.git
EXPOSE 8080
ADD nfllivescores-0.1.0.tar /
ENTRYPOINT ["/nfllivescores-0.1.0/bin/nfllivescores"]
