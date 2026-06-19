# Development Environment Setup

## Prerequisites
- Java 17 or higher
- Maven 3.8+
- Node.js 18+
- npm 9+
- MySQL 8.0 or PostgreSQL 14+
- Git

## Backend Setup (SpringBoot)

### 1. Install Java
```bash
# macOS
brew install openjdk@17

# Ubuntu/Debian
sudo apt-get install openjdk-17-jdk

# Windows
# Download from https://www.oracle.com/java/technologies/downloads/
```

### 2. Install Maven
```bash
# macOS
brew install maven

# Ubuntu/Debian
sudo apt-get install maven
```

### 3. Database Setup

**MySQL:**
```sql
CREATE DATABASE coaching_app;
USE coaching_app;
-- Tables will be created by Hibernate/JPA
```

**PostgreSQL:**
```sql
CREATE DATABASE coaching_app;
```

### 4. Backend Project Setup
```bash
cd backend
mvn clean install
```

### 5. Configure Application Properties

Create `backend/src/main/resources/application-dev.yml`:
```yaml
spring:
  application:
    name: coaching-app
  
  datasource:
    url: jdbc:mysql://localhost:3306/coaching_app
    username: root
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
        format_sql: true
  
  jackson:
    serialization:
      write-dates-as-timestamps: false

server:
  port: 8080
  servlet:
    context-path: /api

jwt:
  secret: your_super_secret_key_change_in_production
  expiration: 86400000  # 24 hours

logging:
  level:
    root: INFO
    com.coaching: DEBUG

file:
  upload-dir: uploads/videos
  max-size: 5368709120  # 5GB
```

### 6. Run Backend
```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

Backend will be running at: `http://localhost:8080/api`

## Frontend Setup (Angular)

### 1. Install Node.js & npm
```bash
# Download from https://nodejs.org/
# or use version manager

# Using nvm (macOS/Linux)
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.0/install.sh | bash
nvm install 18
nvm use 18
```

### 2. Install Angular CLI
```bash
npm install -g @angular/cli@18
```

### 3. Frontend Project Setup
```bash
cd frontend
npm install
```

### 4. Environment Configuration

Create `frontend/src/environments/environment.ts`:
```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api',
  wsUrl: 'ws://localhost:8080/api/ws',
  videoStoragePath: 'http://localhost:8080/api/files'
};
```

### 5. Run Frontend Development Server
```bash
cd frontend
ng serve --open
```

Frontend will be running at: `http://localhost:4200`

## Docker Setup (Optional)

### Create backend Dockerfile
File: `backend/Dockerfile`
```dockerfile
FROM maven:3.8-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/coaching-app-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Create frontend Dockerfile
File: `frontend/Dockerfile`
```dockerfile
FROM node:18-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=builder /app/dist/coaching-app /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

### Docker Compose
File: `docker-compose.yml`
```yaml
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: coaching_app
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

  backend:
    build: ./backend
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/coaching_app
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: root
    depends_on:
      - mysql
    volumes:
      - backend_uploads:/app/uploads

  frontend:
    build: ./frontend
    ports:
      - "4200:80"
    depends_on:
      - backend

volumes:
  mysql_data:
  backend_uploads:
```

### Run with Docker
```bash
docker-compose up --build
```

## Useful Development Commands

### Backend
```bash
# Run tests
mvn test

# Build jar
mvn clean package

# Install dependencies
mvn clean install

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"
```

### Frontend
```bash
# Generate new component
ng generate component modules/dashboard/components/course-card

# Generate service
ng generate service services/course

# Run tests
ng test

# Build for production
ng build --configuration production

# Lint code
ng lint
```

## Debugging

### Backend Debug Mode
```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
```

Then connect debugger to: `localhost:5005`

### Frontend Debug Mode
```bash
ng serve --poll 2000
```

Use Chrome DevTools (F12) for debugging.

## Troubleshooting

### Backend Port Already in Use
```bash
# Find process using port 8080
lsof -i :8080

# Kill process
kill -9 <PID>

# Or run on different port
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

### Frontend Dependencies Issue
```bash
rm -rf node_modules package-lock.json
npm install
```

### Database Connection Error
```bash
# Check MySQL is running
mysql -u root -p

# Create database if missing
CREATE DATABASE coaching_app;
```

## Next Steps

1. Review project structure
2. Set up your IDE (IntelliJ IDEA or VS Code)
3. Install recommended extensions
4. Create feature branches for development
5. Follow the Git workflow
