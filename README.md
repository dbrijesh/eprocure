# eProcurement Portal - Project Management System

A comprehensive, secure, and user-friendly eProcurement Portal application designed to digitally manage procurement projects, plans, and exercises.

## Project Structure

```
EPROCURESDLC/
├── backend/                    # Spring Boot 3 backend
│   ├── src/main/java/com/eprocure/project/
│   │   ├── config/            # Configuration classes
│   │   ├── controller/        # REST controllers
│   │   ├── dto/              # Request/Response DTOs
│   │   ├── entity/           # JPA entities
│   │   ├── exception/        # Custom exceptions
│   │   ├── repository/       # Spring Data repositories
│   │   ├── service/          # Business logic layer
│   │   └── EprocureApplication.java
│   ├── src/main/resources/
│   │   ├── application.yml
│   │   ├── application-dev.yml  # H2 database config
│   │   ├── application-prod.yml # PostgreSQL config
│   │   └── data.sql            # Sample data
│   └── pom.xml
├── frontend/                   # Angular 19 frontend (to be created)
└── README.md
```

## Technology Stack

### Backend
- **Spring Boot** 3.2.1
- **Java** 17+
- **Spring Data JPA** with Hibernate 6.x
- **H2 Database** (development)
- **PostgreSQL** (production)
- **Lombok** for boilerplate reduction
- **MapStruct** for DTO mapping
- **SpringDoc OpenAPI** for API documentation

### Frontend (Next Phase)
- **Angular** 19.x
- **TypeScript** 5.5+
- **Angular Material** 19.x
- **Signals** for state management
- **RxJS** for reactive programming

## Backend Setup and Installation

### Prerequisites
- Java 17 or higher
- Maven 3.9+
- Git

### Backend Installation Steps

1. **Navigate to the backend directory:**
   ```bash
   cd backend
   ```

2. **Build the project:**
   ```bash
   mvn clean install
   ```

3. **Run the application (Development profile with H2):**
   ```bash
   mvn spring-boot:run
   ```

   Or with explicit dev profile:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=dev
   ```

4. **Access the application:**
   - API Base URL: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - H2 Console: `http://localhost:8080/h2-console`
     - JDBC URL: `jdbc:h2:mem:eprocure`
     - Username: `sa`
     - Password: (leave empty)

## REST API Endpoints

### Project Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/v1/projects` | List all projects (paginated) |
| POST | `/v1/projects` | Create a new project |
| GET | `/v1/projects/{id}` | Get project by ID |
| PUT | `/v1/projects/{id}` | Update project |
| DELETE | `/v1/projects/{id}` | Delete project |
| GET | `/v1/projects/statistics` | Get dashboard statistics |

### Query Parameters

- **Pagination:**
  - `page` (default: 0) - Page number
  - `size` (default: 10) - Page size
  - `status` (optional) - Filter by status (DRAFT, ACTIVE, COMPLETED)

### API Response Format

```json
{
  "status": 200,
  "message": "Success",
  "data": { ... },
  "requestId": "uuid",
  "timestamp": 1234567890
}
```

## Sample API Requests

### Create a Project

```bash
curl -X POST http://localhost:8080/v1/projects \
  -H "Content-Type: application/json" \
  -d '{
    "title": "New Procurement Project",
    "description": "Description of the procurement project with at least 10 characters",
    "budget": 150000.00,
    "currency": "EUR",
    "startDate": "2024-03-01",
    "endDate": "2024-12-31",
    "departmentId": "123e4567-e89b-12d3-a456-426614174000",
    "projectManagerId": "123e4567-e89b-12d3-a456-426614174001"
  }'
```

### Get All Projects

```bash
curl http://localhost:8080/v1/projects?page=0&size=10
```

### Get Project Statistics

```bash
curl http://localhost:8080/v1/projects/statistics
```

### Get Project by ID

```bash
curl http://localhost:8080/v1/projects/{project-id}
```

### Update a Project

```bash
curl -X PUT http://localhost:8080/v1/projects/{project-id} \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Updated Project Title",
    "description": "Updated description with sufficient characters",
    "budget": 200000.00,
    "currency": "EUR",
    "startDate": "2024-03-01",
    "endDate": "2024-12-31",
    "departmentId": "123e4567-e89b-12d3-a456-426614174000",
    "projectManagerId": "123e4567-e89b-12d3-a456-426614174001"
  }'
```

### Delete a Project

```bash
curl -X DELETE http://localhost:8080/v1/projects/{project-id}
```

## Database Configuration

### Development (H2 In-Memory)

The application is configured to use H2 in-memory database by default for development.

- **Profile:** `dev` (active by default)
- **Console:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:eprocure`
- **Username:** `sa`
- **Password:** (empty)

Sample data is automatically loaded from `data.sql`.

### Production (Azure PostgreSQL)

To run with PostgreSQL:

1. **Set environment variables:**
   ```bash
   export AZURE_POSTGRES_HOST=your-postgres-host.postgres.database.azure.com
   export AZURE_POSTGRES_DB=eprocure
   export AZURE_POSTGRES_USER=your-username
   export AZURE_POSTGRES_PASSWORD=your-password
   ```

2. **Run with production profile:**
   ```bash
   mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=prod
   ```

## Validation Rules

Projects must satisfy the following validation constraints:

- **Title:** 3-255 characters
- **Description:** 10-2000 characters
- **Budget:** 0.01 to 999,999,999.99
- **Currency:** 3 uppercase letters (e.g., EUR, USD, GBP)
- **Start Date:** Required, must be valid date
- **End Date:** Required, must be >= start date
- **Department ID:** Required, valid UUID
- **Project Manager ID:** Required, valid UUID

## Project Status

Projects can have one of three statuses:

- **DRAFT:** Initial state when created
- **ACTIVE:** Project is in progress
- **COMPLETED:** Project has been finished

## Next Steps - Frontend Implementation

### 1. Create Angular Project

```bash
cd E:\EPROCURESDLC
ng new frontend --standalone --routing --style=scss --skip-git
cd frontend
```

### 2. Install Angular Material

```bash
ng add @angular/material
```

Choose:
- Theme: Custom or Indigo/Pink
- Typography: Yes
- Animations: Yes

### 3. Install Additional Dependencies

```bash
npm install @tanstack/angular-query-experimental
```

### 4. Project Structure to Create

```
frontend/src/app/
├── core/
│   ├── models/
│   │   ├── project.model.ts
│   │   ├── project-status.enum.ts
│   │   ├── api-response.model.ts
│   │   └── statistics.model.ts
│   ├── services/
│   │   ├── project-api.service.ts
│   │   └── project-state.service.ts
│   └── interceptors/
│       ├── api.interceptor.ts
│       └── error.interceptor.ts
├── shared/
│   └── components/
│       ├── sidebar/
│       ├── topbar/
│       ├── stat-card/
│       └── empty-state/
└── features/
    ├── dashboard/
    │   └── components/
    │       ├── statistics-section/
    │       ├── recent-projects/
    │       ├── quick-actions/
    │       └── project-status-section/
    └── projects/
        ├── project-list/
        ├── project-form/
        └── project-detail/
```

### 5. Environment Configuration

**src/environments/environment.ts:**
```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'
};
```

### 6. Run Frontend Development Server

```bash
npm start
# or
ng serve
```

Access at: `http://localhost:4200`

## Testing the Backend

### 1. Using Swagger UI

1. Start the backend: `mvn spring-boot:run`
2. Open browser: `http://localhost:8080/swagger-ui.html`
3. Try out the API endpoints directly from the UI

### 2. Using cURL (see examples above)

### 3. Using Postman

Import the OpenAPI spec from: `http://localhost:8080/api-docs`

## Features Implemented

### Backend (Complete)
- ✅ Project CRUD operations
- ✅ Pagination support
- ✅ Dashboard statistics calculation
- ✅ Input validation
- ✅ Error handling
- ✅ API documentation (Swagger)
- ✅ H2 database configuration
- ✅ Azure PostgreSQL configuration
- ✅ CORS configuration for Angular
- ✅ Sample data loading

### Frontend (To Be Implemented)
- ⏳ Angular project setup
- ⏳ TypeScript models
- ⏳ API service layer
- ⏳ State management with Signals
- ⏳ Dashboard with statistics cards
- ⏳ Project list with pagination
- ⏳ Project create/edit forms
- ⏳ Sidebar and topbar navigation
- ⏳ Dark mode toggle
- ⏳ Responsive design

## Dashboard Statistics

The `/v1/projects/statistics` endpoint provides:

- **Total Projects:** Count of all projects
- **Active Projects:** Count of active projects
- **Active Projects Change %:** Percentage change vs last month
- **Completed Projects:** Count of completed projects
- **Draft Projects:** Count of draft projects
- **Total Budget:** Sum of all project budgets (in EUR)

## Troubleshooting

### Backend won't start

1. **Check Java version:**
   ```bash
   java -version
   ```
   Should be 17 or higher.

2. **Check if port 8080 is in use:**
   ```bash
   netstat -ano | findstr :8080  # Windows
   lsof -i :8080                 # Mac/Linux
   ```

3. **Clean and rebuild:**
   ```bash
   mvn clean install
   ```

### Database connection issues

1. **For H2:** Check `application-dev.yml` configuration
2. **For PostgreSQL:** Verify environment variables are set correctly

### CORS errors

If you get CORS errors when connecting from Angular:
- Ensure backend `CorsConfig.java` includes your frontend URL
- Default allowed: `http://localhost:4200`

## Deployment

### Backend Deployment

1. **Build JAR:**
   ```bash
   mvn clean package -Pprod
   ```

2. **Run JAR with production profile:**
   ```bash
   java -jar target/project-service-1.0.0.jar --spring.profiles.active=prod
   ```

### Environment Variables for Production

```bash
AZURE_POSTGRES_HOST=<your-host>
AZURE_POSTGRES_DB=eprocure
AZURE_POSTGRES_USER=<username>
AZURE_POSTGRES_PASSWORD=<password>
```

## License

Proprietary - eProcure Team

## Support

For support, please contact: support@eprocure.com
