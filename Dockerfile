FROM ubuntu:latest
LABEL authors="egor2"

ENTRYPOINT ["top", "-b"]