# demo-springboot
Demo Java Springboot application on Kubernetes environment.

[![](https://img.shields.io/badge/Owner-minhluantran017-darkviolet)](mailto:minhluantran017@gmail.com)
![](https://img.shields.io/badge/-microservices-green)
![](https://img.shields.io/badge/-Java-red)
![](https://img.shields.io/badge/-Docker-blue)
![](https://img.shields.io/badge/-Kubernetes-blue)
![](https://img.shields.io/badge/-Jenkins-orange)
![](https://img.shields.io/badge/-Helm-blue)

***PROJECT STATUS:***

- [x] Develop
- [x] Build
- [x] Bake
- [x] Deploy
- [ ] Test
- [ ] Release

## Getting Started

### Prerequisites

* `docker`, `docker-compose`
* `helm`
* `kubectl`
* A Kubernetes cluster (eg. minikube, microK8s, EKS, ...)

### Cloning code

```sh
git clone https://github.com/minhluantran017/demo-springboot.git
cd demo-springboot
```

The directory structure is as below:
```
(root)
|___artifacts           --->> Store binaries for each steps
|___build               --->> Contains build scripts
|   |___docker
|___ci                  --->> Contains CI/CD workflows
|___deploy              --->> Contains deploy scripts
|   |___docker-compose
|   |___helm
|___lib                 --->> Contains shared libraries
|___src                 --->> Contains source code
|___tests               --->> Contains automation test scripts
|   |___integration
|   |___functional
|   |___performance
|___pom.xml
|___README.md
```

### Creating war file

You should specify the `VERSION_STRING` environment variables 
before taking next steps:

```sh
export VERSION_STRING=$(git describe --tags --long --always --dirty)
echo $VERSION_STRING
```

Compile Java and create WAR file with Apache maven:

```sh
docker run -i -t --rm -v "$HOME"/.m2:/root/.m2 \
    -v "$PWD":/app -w /app maven:3-jdk-8 \
    mvn clean package -DversionString=${VERSION_STRING}
```

Produced artifacts should be moved to `artifacts` directory to push to artifactory:

```sh
rm -rf artifacts && mkdir -p artifacts
cp target/*.war artifacts/demo-springboot.war
```

### Building the image

Build application Docker image:

```sh
docker build -f build/docker/Dockerfile . -t demo-springboot:${VERSION_STRING}
```

Push Docker image to Docker repository:
```sh
# Log into the Docker repository
### If you use Docker Hub...
YOUR_REPO=minhluantran017
docker login

### If you use AWS ECR (Elastic Container Registry)
YOUR_REPO=xxxxx.dkr.ecr.<region>.amazonaws.com
$(aws ecr get-login)


# Tag your image
docker tag demo-springboot:${VERSION_STRING} \
    ${YOUR_REPO}/demo-springboot:${VERSION_STRING}

# Push Docker image to repository:
docker push ${YOUR_REPO}/demo-springboot:${VERSION_STRING}
```

### Deploying application

#### Local machine

Bring up the local stack with `docker-compose`:

```sh
docker-compose -f deploy/docker-compose/compose.yml up
```

Then, these endpoints are accessible: 
- http://localhost:8080/demo-springboot/api/v1/products
- http://localhost:8080/demo-springboot/api/v2/products

To tear down:

```sh
docker-compose -f deploy/docker-compose/compose.yml down -v
```

#### Kubernetes cluster

We use helm chart to deploy application onto Kubernetes.

```sh
helm install phone-price deploy/helm/demo-springboot/ \
   --set ImageTag="${VERSION_STRING}"
```

Since this is for demo purpose only, we do not have Ingress.
Port forwarding is the best choice:

```sh
export POD_NAME=$(kubectl get pods --namespace default -l "app.kubernetes.io/name=demo-springboot,app.kubernetes.io/instance=productv2" -o jsonpath="{.items[0].metadata.name}")
kubectl --namespace default port-forward ${POD_NAME} 8080:8080
```

### Running test suites

WIP

## CI/CD integration

This project is configured for CI/CD on Jenkins.
All configurations are under `ci` directory.

## Branching

* **master** - *Master branch*
* **dev** - *Dev branch for development and testing*

## Authors

* **Luan Tran** - *Owner* - [Github](https://github.com/minhluantran017) - [Email](mailto:minhluantran017@gmail.com)

## License

This project has no license.

But if you decide to fork or get idea from this, feel free to :star: me. Thanks :wink:
