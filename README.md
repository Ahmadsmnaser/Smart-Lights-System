# Smart Lights System

Smart Lights is a full-stack demo app with a Spring Boot backend that manages simulated light devices using SQL Server for device data, MongoDB for event history, and Quartz for scheduled toggling, paired with an Angular frontend that displays lights, history, and scheduling controls through REST APIs. 

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot, Spring Data JPA, Spring Data MongoDB |
| Scheduler | Quartz Scheduler |
| Databases | SQL Server (device state), MongoDB (event history) |
| Frontend | Angular 14 |
| Testing | Cucumber (BDD), Selenium |
| Infrastructure | Docker Compose (dev + QA environments) |

## Architecture Overview

```
Angular UI → Spring Boot REST API → SQL Server (device metadata)
                                 ↘ MongoDB (event logs)
                                 ↘ Quartz (automated toggling)
```

## Features

- List and manage simulated light devices
- Manual toggle + pause/resume automation
- Quartz-based scheduled toggling with configurable intervals
- Custom schedule rules per device
- Event history (last 10 events per device, stored in MongoDB)
- Auto-seeding of devices on startup
- Separate dev/QA Docker environments

## Project Structure

```
.
├── smart-lights-api/        # Spring Boot backend
├── smart-lights-ui/         # Angular frontend
├── docker-compose.dev.yml   # Dev environment (ports 1433, 27017)
└── docker-compose.qa.yml    # QA environment (ports 1434, 27018)
```

## Getting Started

### 1. Start infrastructure

```bash
docker compose -f docker-compose.dev.yml up -d
```

### 2. Run backend

```bash
cd smart-lights-api
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### 3. Run frontend

```bash
cd smart-lights-ui/smart-lights-ui
npm install && npm start
```

Open `http://localhost:4200`

## API Reference

Base URL: `http://localhost:8080/api/lights`

| Method | Endpoint | Description |
|---|---|---|
| GET | `/` | List all lights |
| PUT | `/{id}/toggle` | Toggle light state |
| PUT | `/{id}/pause` | Pause scheduler |
| PUT | `/{id}/resume` | Resume scheduler |
| GET | `/{id}/history` | Last 10 events |
| GET | `/{id}/schedule` | Get schedule |
| PUT | `/{id}/schedule` | Update interval |
| POST | `/{id}/schedule` | Add rule |
| DELETE | `/{id}/schedule/{ruleId}` | Remove rule |

## Configuration

Profile-based config under `src/main/resources/`:

- `application-dev.properties` → SQL Server on `1433`, MongoDB on `27017`
- `application-qa.properties` → SQL Server on `1434`, MongoDB on `27018`
