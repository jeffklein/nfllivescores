FROM aglover/java8-pier
MAINTAINER Jeff Klein "jeff@jeffklein.org"
EXPOSE 8080
ADD nfllivescores-0.1.0.tar /
ENTRYPOINT ["/nfllivescores-0.1.0/bin/nfllivescores"]
