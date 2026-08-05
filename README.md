# DevOps Monitoring Portal

Production-ready Spring Boot 3.x monitoring portal for deployment on existing AKS, ACR, and GitHub Actions infrastructure.

## Tech Stack

- Java 21
- Spring Boot 3.3.x
- Maven
- Spring Web, Data JPA, Validation, Security, Actuator, Thymeleaf
- MySQL
- Lombok
- Springdoc OpenAPI (Swagger UI)

## Features

- Form-based authentication with in-memory admin user
- Responsive Bootstrap 5 dashboard UI
- Deployment metadata page
- Health details page
- Actuator endpoints:
  - `/actuator/health`
  - `/actuator/info`
  - `/actuator/metrics`
  - `/actuator/prometheus`
- REST APIs:
  - `GET /api/dashboard`
  - `GET /api/system`
  - `GET /api/metrics`
  - `GET /api/build`
  - `GET /api/health`
- Persistent tables:
  - `application_log`
  - `deployment_history`

## Project Structure

- `src/main/java/.../controller` - MVC and REST controllers
- `src/main/java/.../service` - service layer
- `src/main/java/.../repository` - repository layer
- `src/main/java/.../entity` - JPA entities
- `src/main/resources/templates` - Thymeleaf pages
- `src/main/resources/schema.sql` - DB schema init
- `k8s/` - Kubernetes manifests
- `.github/workflows/ci-cd.yml` - GitHub Actions workflow

## Configuration

Set environment variables (or keep defaults for local testing):

- `MYSQL_URL`
- `MYSQL_USERNAME`
- `MYSQL_PASSWORD`
- `ADMIN_USERNAME`
- `ADMIN_PASSWORD`
- `APP_VERSION`
- `BUILD_NUMBER`
- `BUILD_TIMESTAMP`
- `APP_ENVIRONMENT`
- `IMAGE_TAG`
- `GIT_COMMIT_ID`
- `BRANCH_NAME`
- `DEPLOYMENT_TIME`

## Build and Run

```bash
mvn clean package
java -jar target/devops-monitoring-portal-1.0.0.jar
```

Application runs on port `8080`.

## Docker

```bash
docker build -t devops-monitoring-portal:latest .
docker run -p 8080:8080 devops-monitoring-portal:latest
```

## Kubernetes Deployment

Apply manifests against an existing cluster:

```bash
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/secret.yaml
kubectl apply -f k8s/service.yaml
kubectl apply -f k8s/deployment.yaml
```

## GitHub Actions Secrets Required

- `AZURE_CREDENTIALS`
- `ACR_NAME`
- `ACR_LOGIN_SERVER`
- `AKS_RESOURCE_GROUP`
- `AKS_CLUSTER_NAME`

## Swagger UI

- `http://<host>:8080/swagger-ui/index.html`

## SQL Script

- `sql/schema.sql`
