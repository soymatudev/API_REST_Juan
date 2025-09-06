FROM ubuntu:latest
LABEL authors="numbe"

ENTRYPOINT ["top", "-b"]