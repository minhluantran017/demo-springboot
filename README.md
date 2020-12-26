# demo-springboot
Demo Java Springboot micro-services application on Kubernetes environment.

![](https://img.shields.io/badge/Environment-Kubernetes-blue)
[![](https://img.shields.io/badge/Owner-minhluantran017-darkviolet)](mailto:minhluantran017@gmail.com)

Tags: <mark>DevOps</mark>, <mark>Java</mark>, <mark>microservices</mark>, <mark>Kubernetes</mark>

***PROJECT STATUS:***

[![CircleCI](https://circleci.com/gh/minhluantran017/demo-springboot.svg?style=svg)](https://circleci.com/gh/minhluantran017/demo-springboot)

- [x] Development
- [x] Building and Packaging
- [ ] Deployment
- [ ] Testing

## Getting Started

### Prerequisites

* Docker
* Helm
* kubectl
* A Kubernetes cluster to deploy onto

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

You should specify the `PRODUCT_RELEASE` and `BUILD_NUMBER` environment variables 
before taking next steps:

```sh
export PRODUCT_RELEASE=2.0.0
export BUILD_NUMBER=01
```

```sh
docker run -i -t --rm -v "$HOME"/.m2:/root/.m2 \
    -v "$PWD":/usr/src/app -w /usr/src/app maven:3-jdk-8 \
    mvn clean package \
    -Drelease=${PRODUCT_RELEASE} \
    -DbuildNumber=${BUILD_NUMBER}

rm -rf artifacts && mkdir -p artifacts
cp target/*.war artifacts/demo-springboot.war
```

### Building the image

Build application Docker image:

```sh
docker build -f build/docker/app.dockerfile . -t demo-springboot_app:${PRODUCT_RELEASE}-${BUILD_NUMBER}
```

Build database Docker image:

```sh
docker build -f build/docker/db.dockerfile . -t demo-springboot_db:${PRODUCT_RELEASE}-${BUILD_NUMBER}
```

Push Docker image to Docker repository:
```sh
# Log into the Docker repository
### If you use Docker Hub...
YOUR_REPO=minhluantran017 # This is an example
docker login

### If you use AWS ECR (Elastic Container Registry)
YOUR_REPO=xxxxx.dkr.ecr.<region>.amazonaws.com
$(aws ecr get-login)


# Tag your images
docker tag demo-springboot_app:${PRODUCT_RELEASE}-${BUILD_NUMBER} \
    ${YOUR_REPO}/demo-springboot_app:${PRODUCT_RELEASE}-${BUILD_NUMBER}
docker tag demo-springboot_db:${PRODUCT_RELEASE}-${BUILD_NUMBER} \
    ${YOUR_REPO}/demo-springboot_db:${PRODUCT_RELEASE}-${BUILD_NUMBER}

# Push Docker images to repository:
docker push ${YOUR_REPO}/demo-springboot_app:${PRODUCT_RELEASE}-${BUILD_NUMBER}
docker push ${YOUR_REPO}/demo-springboot_db:${PRODUCT_RELEASE}-${BUILD_NUMBER}
```

### Deploying application

#### Local machine

```sh
docker-compose -f deploy/docker-compose/compose.yml up
```

Then, these endpoints are accessible: 
- http://localhost:8080/demo-springboot/api/v1/products
- http://localhost:8080/demo-springboot/api/v2/products

To tear down:

```sh
docker-compose -f deploy/docker-compose/compose.yml down
```

#### Kubernetes cluster

```sh
helm install phone-price deploy/helm/demo-springboot/
export POD_NAME=$(kubectl get pods --namespace default -l "app.kubernetes.io/name=demo-springboot,app.kubernetes.io/instance=productv2" -o jsonpath="{.items[0].metadata.name}")
kubectl --namespace default port-forward ${POD_NAME} 8080:8080
```

### Running test suites

WIP

## CI/CD integration

This project is configured for CI/CD on Jenkins and CircleCI.
All configurations are under `.jenkins`/`.circleci` directory.

More CI/CD tools will be added (depends on my freetime).

## Branching

* **master** - *Master branch*
* **dev** - *Dev branch for development and testing*

## Authors

* **Luan Tran** - *Owner* - [Github](https://github.com/minhluantran017) - [Email](mailto:minhluantran017@gmail.com)

## License

This project has no license.

But if you decide to fork or get idea from this, feel free to :star: me. Thanks :wink:
