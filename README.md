# DevOps Demo Spring Boot Project

This is a simple Spring Boot application designed to demonstrate core DevOps concepts:
- **Build**: Maven
- **Containerization**: Docker
- **Orchestration**: Docker Compose & Kubernetes
- **CI/CD**: GitHub Actions
- **Observability**: Spring Boot Actuator

## Prerequisites
- **Java 17+**: Ensure you have Java 17 installed and active.
    - Check with `java -version`.
    - If needed, export the path: `export JAVA_HOME="/Users/mrvu/Library/Java/JavaVirtualMachines/corretto-17.0.10/Contents/Home"` (example based on your system).
- Maven (optional, wrapper/docker provided)
- Docker & Docker Compose

## Running Locally

### 1. Using Maven
```bash
mvn spring-boot:run
```
Access at: `http://localhost:8080/`

### 2. Using Docker Compose
```bash
docker compose up --build
```
Access at: `http://localhost:8080/`

> [!NOTE]
> If you see `command not found: docker` or `docker-compose`, ensure Docker Desktop is installed and running.
> You might need to add Docker to your PATH or `export PATH="$PATH:/Applications/Docker.app/Contents/Resources/bin"`


## Endpoints
- **Home**: `GET /` - Returns a hello message with timestamp.
- **Health**: `GET /actuator/health` - Apps status.
- **Metrics**: `GET /actuator/metrics` - Application metrics.

## DevOps Structure
- **Dockerfile**: Multi-stage build (Builder -> Runtime) for optimized image size.
- **.github/workflows/ci.yml**: CI pipeline that builds the code and the Docker image on push/PR to main.
- **k8s/**: Kubernetes manifests (`deployment.yaml` and `service.yaml`) for deploying to a cluster.

## Deployment to Kubernetes (Local K8s like Minikube/K3s)

1. Build image inside minikube (or push to registry):
    ```bash
    eval $(minikube docker-env)
    docker build . -t devops-demo:latest
    ```
2. Apply manifests:
    ```bash
    kubectl apply -f k8s/
    ```
3. Check status:
    ```bash
    kubectl get all
    ```
