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

- Install `kind` according to the instructions on the website https://kind.sigs.k8s.io/

- Сheck if `kubectl` is installed
  ```
  kubectl version
  ```
- Create cluster
  ```
  kind create cluster --config .\kind-config.yaml
  ```
- Install Ingress NGINX plugin
  ```
  kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml
  ```
- Next
  ```
  kubectl wait --namespace ingress-nginx --for=condition=ready pod --selector=app.kubernetes.io/component=controller --timeout=90s
  ```
- Wait for ingress to start. Don't forget to check and change your port in `deployment.yaml`, then start pods
  ```
  kubectl apply -f .\k8s\deployment.yaml
  ```
    - to watch pods:
      ```
      kubectl get pods --watch
      ```
    - logs of same pod:
      ```
      kubectl logs <pod-name>
      ```
    - to kill all pods:
      ```
      kubectl delete pods --all
      ```
    - to kill all deployments:
      ```
      kubectl delete deployments --all
      ```
    - forward port of pod:
      ```
      kubectl port-forward <pod-name> 8899:8080
      ```
- Start service
  ```
  kubectl apply -f .\k8s\service.yaml
  ```
    - to watch services:
      ```
      kubectl get service
      ```
- Start ingress
  ```
  kubectl apply -f .\k8s\ingress.yaml
  ```
    - to watch services:
      ```
      kubectl get ingress
      ```

#### Using the service

To use the service, use `Postman` or a similar application, you can use `cURL`.
`POST` request to `localhost:8888`,
example request body:
```
{
    "firstname": "John",
    "lastname": "Black",
    "phone": "+1343782648",
    "email": "mr.grey@spyder.hd"
}
```

To use the service without `kubernetes` and `docker` use swagger interface:

http://localhost:8080/swagger-ui.html

for `docker` without `kubernetes`:

http://localhost:8081/swagger-ui.html

#### Delete service

    kind delete cluster

    docker ps -a

    docker rm -f <container-id>

    mvn clean
