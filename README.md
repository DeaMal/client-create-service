# client-create-service

### Service is to for save client entity in database

---

#### Start db in docker:
    
    docker-compose -f .\compose-env.yaml up -d

#### Docker build image:

    mvn clean install

For docker build you need account on https://hub.docker.com/ 

    docker login

    docker build . -t <your_account>/client-create-service:0.0.1

#### Docker run:

You need find your IP using `ipconfig` for set in `DATASOURCE_HOST`

    docker run -ti --rm -e DATASOURCE_HOST=192.168.56.1 -p 8081:8080 <your_account>/client-create-service:0.0.1

#### Docker push:

    docker push <your_account>/client-create-service:0.0.1

#### Docker pull:

    docker pull <your_account>/client-create-service:0.0.1

#### Delete docker image:

    docker rmi <your_account>/client-create-service:0.0.1

#### Next step: running local Kubernetes clusters using Docker container

https://kind.sigs.k8s.io/
