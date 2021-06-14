FROM jenkins
USER root
RUN apt-get update && apt-get install -y docker.io 238 && rm -rf /var/lib/apt/lists/*
user jenkins
