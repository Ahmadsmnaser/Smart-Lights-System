# Smart Lights

A full-stack smart lighting demo application with a Spring Boot backend and Angular frontend.

## Short Description

`smart-lights` is a demo system for managing simulated smart lights. The backend is a Spring Boot API that stores device metadata in SQL Server, logs light events in MongoDB, and uses Quartz scheduling to toggle lights on a configurable interval. The frontend is an Angular web app that consumes the API to show lights, history, and scheduling controls.

## Project Structure

- `smart-lights-api/` — Spring Boot backend
  - Java 17
  - Spring Boot 2.6.4
  - Spring Data JPA with SQL Server
  - Spring Data MongoDB for event history
  - Quartz scheduler for automated light toggling
  - Cucumber + Selenium test dependencies for integration tests

- `smart-lights-ui/smart-lights-ui/` — Angular frontend
  - Angular 14
  - REST client for `http://localhost:8080/api/lights`
  - Components for device list, history, and scheduling

- `docker-compose.dev.yml` — development services
  - SQL Server on port `1433`
  - MongoDB on port `27017`

- `docker-compose.qa.yml` — QA services
  - SQL Server on port `1434`
  - MongoDB on port `27018`

## Features

- List all lights
- Pause and resume light automation
- Toggle light state manually
- View last 10 light history events
- Configure schedule interval per light
- Add and remove custom schedule rules
- Auto-seeding of lights at startup using `app.devices.count`

## Backend API Endpoints

Base path: `http://localhost:8080/api/lights`

- `GET /api/lights` — fetch all lights
- `PUT /api/lights/{id}/pause` — pause the light scheduler
- `PUT /api/lights/{id}/resume` — resume the light scheduler
- `PUT /api/lights/{id}/toggle` — toggle the light and log the event
- `GET /api/lights/{id}/history` — get last 10 logs for the light
- `PUT /api/lights/{id}/schedule` — update schedule interval
- `POST /api/lights/{id}/schedule` — add schedule rule
- `DELETE /api/lights/{id}/schedule/{ruleId}` — remove schedule rule
- `GET /api/lights/{id}/schedule` — get current schedule and rules

## Configuration

### Backend

Use profile-specific properties:

- `smart-lights-api/src/main/resources/application-dev.properties`
- `smart-lights-api/src/main/resources/application-qa.properties`

Development settings:

- SQL Server URL: `jdbc:sqlserver://localhost:1433;databaseName=lights_dev_db`
- MongoDB URL: `mongodb://localhost:27017/lights_log_dev_db`
- Device count: `app.devices.count=5`

QA settings:

- SQL Server URL: `jdbc:sqlserver://localhost:1434;databaseName=lights_qa_db`
- MongoDB URL: `mongodb://localhost:27018/lights_log_qa_db`
- Device count: `app.devices.count=3`

### Frontend

The Angular service is configured to call the backend at:

- `http://localhost:8080/api/lights`

If you need to change it, update `smart-lights-ui/smart-lights-ui/src/app/services/light.service.ts`.

## Local Setup

### 1. Start infrastructure services

```bash
cd /home/ahmad/smart-lights
docker compose -f docker-compose.dev.yml up -d
```

### 2. Run the backend

```bash
cd smart-lights-api
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

If `./mvnw` is not executable, run:

```bash
chmod +x mvnw
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### 3. Run the frontend

```bash
cd smart-lights-ui/smart-lights-ui
npm install
npm start
```

Then open `http://localhost:4200`.

## Notes

- The backend seeds light records automatically at startup if the SQL table is empty.
- The history log is stored in MongoDB and only the most recent 10 events are returned per light.
- The scheduler is bootstrapped by Quartz on startup and toggles lights using the configured interval.

## Useful Commands

- Build backend: `./mvnw clean package`
- Run backend tests: `./mvnw test`
- Build frontend: `npm run build`
- Run frontend unit tests: `npm test`

## Recommended Workflow

1. Start Docker services.
2. Start the backend with `dev` profile.
3. Start the Angular frontend.
4. Use the UI to interact with lights and observe history updates.

---

This README provides the full project overview, startup instructions, architecture summary, and the main API contract.
