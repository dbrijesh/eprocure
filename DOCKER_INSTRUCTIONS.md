# Docker Instructions for eProcure Portal

This document provides instructions for building and running the backend and frontend Docker containers separately.

## Prerequisites

- Docker Desktop installed and running
- Docker version 20.10 or higher

## Backend (Spring Boot)

### Build the Backend Docker Image

```bash
cd backend
docker build -t eprocure-backend:latest .
```

### Run the Backend Container

**Development mode (H2 Database):**
```bash
docker run -d \
  --name eprocure-backend \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=dev \
  eprocure-backend:latest
```

**Production mode (Azure PostgreSQL):**
```bash
docker run -d \
  --name eprocure-backend \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e AZURE_POSTGRES_HOST=your-postgres-host \
  -e AZURE_POSTGRES_DB=your-database-name \
  -e AZURE_POSTGRES_USER=your-username \
  -e AZURE_POSTGRES_PASSWORD=your-password \
  eprocure-backend:latest
```

### Backend Container Management

**View logs:**
```bash
docker logs eprocure-backend
docker logs -f eprocure-backend  # Follow logs
```

**Stop container:**
```bash
docker stop eprocure-backend
```

**Start existing container:**
```bash
docker start eprocure-backend
```

**Remove container:**
```bash
docker rm eprocure-backend
```

**Remove image:**
```bash
docker rmi eprocure-backend:latest
```

### Backend Access

- **Application:** http://localhost:8080
- **API Documentation (Swagger):** http://localhost:8080/swagger-ui.html
- **H2 Console (dev mode):** http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:eprocuredb`
  - Username: `sa`
  - Password: `password`

## Frontend (Angular)

### Build the Frontend Docker Image

```bash
cd frontend
docker build -t eprocure-frontend:latest .
```

**Note:** The build process takes 5-10 minutes as it installs dependencies and compiles the Angular application.

### Run the Frontend Container

```bash
docker run -d \
  --name eprocure-frontend \
  -p 4200:80 \
  eprocure-frontend:latest
```

**If backend is also in Docker on the same machine:**
```bash
docker run -d \
  --name eprocure-frontend \
  -p 4200:80 \
  --add-host=host.docker.internal:host-gateway \
  eprocure-frontend:latest
```

### Frontend Container Management

**View logs:**
```bash
docker logs eprocure-frontend
docker logs -f eprocure-frontend  # Follow logs
```

**Stop container:**
```bash
docker stop eprocure-frontend
```

**Start existing container:**
```bash
docker start eprocure-frontend
```

**Remove container:**
```bash
docker rm eprocure-frontend
```

**Remove image:**
```bash
docker rmi eprocure-frontend:latest
```

### Frontend Access

- **Application:** http://localhost:4200

## Running Both Containers Together

### Option 1: Run containers on the same Docker network

**Create a network:**
```bash
docker network create eprocure-network
```

**Run backend:**
```bash
docker run -d \
  --name eprocure-backend \
  --network eprocure-network \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=dev \
  eprocure-backend:latest
```

**Run frontend:**
```bash
docker run -d \
  --name eprocure-frontend \
  --network eprocure-network \
  -p 4200:80 \
  eprocure-frontend:latest
```

### Option 2: Use host networking (Linux only)

**Backend:**
```bash
docker run -d \
  --name eprocure-backend \
  --network host \
  -e SPRING_PROFILES_ACTIVE=dev \
  eprocure-backend:latest
```

**Frontend:**
```bash
docker run -d \
  --name eprocure-frontend \
  --network host \
  eprocure-frontend:latest
```

## Troubleshooting

### Backend Issues

**Container won't start:**
```bash
# Check logs
docker logs eprocure-backend

# Check if port 8080 is already in use
netstat -an | grep 8080  # Linux/Mac
netstat -an | findstr 8080  # Windows
```

**Database connection issues:**
- Ensure PostgreSQL is accessible from Docker container
- Verify environment variables are correct
- Check database credentials

### Frontend Issues

**Container won't start:**
```bash
# Check logs
docker logs eprocure-frontend

# Check if port 4200 (or 80) is already in use
netstat -an | grep 4200  # Linux/Mac
netstat -an | findstr 4200  # Windows
```

**Cannot connect to backend:**
- Ensure backend is running and accessible
- Check CORS configuration in backend
- Update `environment.prod.ts` with correct backend URL before building image

### General Docker Commands

**List all containers:**
```bash
docker ps -a
```

**List all images:**
```bash
docker images
```

**Stop all containers:**
```bash
docker stop $(docker ps -aq)
```

**Remove all stopped containers:**
```bash
docker rm $(docker ps -aq)
```

**Remove all unused images:**
```bash
docker image prune -a
```

**View Docker disk usage:**
```bash
docker system df
```

**Clean up Docker system:**
```bash
docker system prune -a --volumes
```

## Performance Tips

### Backend
- Allocate at least 1GB RAM to backend container
- Use appropriate JVM memory settings for production
- Monitor heap usage with JVM tools

### Frontend
- Nginx serves static files efficiently
- Gzip compression is enabled by default
- Static assets are cached for 1 year

## Security Notes

1. **Backend:**
   - Runs as non-root user `spring`
   - Health check endpoint enabled
   - Uses multi-stage build to reduce image size

2. **Frontend:**
   - Runs as non-root user `nginx-user`
   - Security headers configured in nginx
   - Gzip compression enabled

3. **Production Deployment:**
   - Use secrets management for sensitive data
   - Enable HTTPS/TLS
   - Configure proper CORS settings
   - Use environment-specific configurations
   - Implement rate limiting
   - Regular security updates

## Build Arguments (Advanced)

### Backend with custom Maven settings:
```bash
docker build -t eprocure-backend:latest \
  --build-arg MAVEN_OPTS="-Xmx1024m" \
  .
```

### Frontend with custom build configuration:
```bash
docker build -t eprocure-frontend:latest \
  --build-arg CONFIGURATION=production \
  .
```

## Updating Applications

**After code changes:**

1. Rebuild the image:
   ```bash
   docker build -t eprocure-backend:latest .
   # or
   docker build -t eprocure-frontend:latest .
   ```

2. Stop and remove old container:
   ```bash
   docker stop eprocure-backend
   docker rm eprocure-backend
   ```

3. Run new container:
   ```bash
   docker run -d --name eprocure-backend -p 8080:8080 eprocure-backend:latest
   ```

## Support

For issues or questions, refer to:
- Backend: Check logs at `/app/logs` inside container
- Frontend: Check nginx logs at `/var/log/nginx/` inside container
- API Documentation: http://localhost:8080/swagger-ui.html
