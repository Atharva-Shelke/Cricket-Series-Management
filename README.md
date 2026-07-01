# 🏏 Cricket Series Management API (Spring Boot + JDBC + PostgreSQL)
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-4.x-brightgreen)
![Database](https://img.shields.io/badge/Database-PostgreSQL-blue)
![Deployed on Render](https://img.shields.io/badge/Deployed-Render-purple)

A RESTful backend application built using Spring Boot that manages **cricket series and matches** using **pure JDBC (NamedParameterJdbcTemplate)** with a **PostgreSQL** database.

---

## 🌐 Live Demo

🔗 https://cricket-series-management.onrender.com/health

Try:
- `/cricket/series` : https://cricket-series-management.onrender.com/cricket/series?includeMatches=false
- `/cricket/series/{id}` : https://cricket-series-management.onrender.com/cricket/series/6?includeMatches=true
- `/cricket/match/{id}` : https://cricket-series-management.onrender.com/cricket/match/4

---

## 📌 Features

* Create, Read, Update, Delete (CRUD) operations for Series & Matches
* Add matches while creating a series (transactional)
* Fetch series with or without matches using query parameter
* Uses Spring JDBC and NamedParameterJdbcTemplate - no ORM (no JPA/Hibernate)
* Manual SQL queries (strong SQL focus)
* JSON aggregation for optimized queries (avoids N+1 problem)
* Proper error handling with HTTP status responses
* Clean layered architecture (Controller → Service → DAO)
* DTO pattern to separate API contract from DB models

---

## 🚀 Tech Stack

* Java 17
* Spring Boot
* Spring Web
* Spring JDBC (NamedParameterJdbcTemplate)
* PostgreSQL
* Jackson (JSON processing)
* Maven

---

## 🗂️ Project Structure
```
src/
└── main/
  ├── java/com/cricketSeries/
  │   ├── controller    # REST endpoints
  │   ├── service       # Business logic
  │   ├── dao           # Database access (JDBC)
  │   ├── model         # DB entities
  │   ├── dto           # Request/Response objects
  │   └── utility       # SQL loader
  └── resources/
      ├── application.properties    # configuration file
      └── sql                       # SQL queries file
```

---

## ⚙️ Database Setup

This project uses PostgreSQL.

### Create Tables

```sql
CREATE TABLE series (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    location VARCHAR(100),
    start_date DATE,
    end_date DATE
);

CREATE TABLE match (
    id SERIAL PRIMARY KEY,
    series_id INT REFERENCES series(id) ON DELETE CASCADE,
    team_a VARCHAR(100),
    team_b VARCHAR(100),
    match_date DATE,
    venue VARCHAR(100),
    match_type VARCHAR(50)
);

CREATE INDEX idx_match_series_id ON match(series_id);
```
---
## 🔧 Configuration

Update `application.properties`:

```properties
spring.application.name=CricketSeriesManagement

# port for server
server.port=${PORT:8088}

# Database configuration
spring.datasource.url=jdbc:postgresql://${DB_HOST}/${DB_NAME}?sslmode=require

spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver
```
---

## ▶️ Running the Application

### Using Maven Wrapper:

```bash
./mvnw spring-boot:run
```

Or:

```bash
mvn spring-boot:run
```

### Using Docker:

#### Build Docker Image

```bash
docker build -t cricket-series-management .
```

#### Run Docker Container

```bash
docker run -p 8088:8088 cricket-series-management
```

---

## 🌐 API Endpoints

### 📌 Series APIs

| Method | Endpoint | Description |
|--------|----------|------------|
| POST   | `/cricket/series` | Create a new series (with optional matches) |
| GET    | `/cricket/series?includeMatches=false` | Get all series |
| GET    | `/cricket/series?includeMatches=true` | Get all series with matches |
| GET    | `/cricket/series/{id}?includeMatches=false` | Get series by ID |
| GET    | `/cricket/series/{id}?includeMatches=true` | Get series by ID with matches |
| PUT    | `/cricket/series/{id}` | Update series |
| DELETE | `/cricket/series/{id}` | Delete series |

---

### 📌 Match APIs

| Method | Endpoint | Description |
|--------|----------|------------|
| POST   | `/cricket/series/{seriesId}/match` | Add match to a series |
| GET    | `/cricket/series/{seriesId}/match` | Get all matches of a series |
| GET    | `/cricket/match/{matchId}` | Get match by ID |
| PUT    | `/cricket/match/{matchId}` | Update match |
| DELETE | `/cricket/match/{matchId}` | Delete match |

---

### 📌 Sample Request

- Series

```json
{
  "name": "IPL 2026",
  "location": "India",
  "startDate": "2026-03-20",
  "endDate": "2026-05-30",
  "matches": [
    {
      "teamA": "MI",
      "teamB": "CSK",
      "matchDate": "2026-03-21",
      "venue": "Mumbai",
      "matchType": "T20"
    }
  ]
}
```

---

## ⚠️ Error Handling

* Returns meaningful messages when:
  * Series or Match ID does not exist
* Uses HTTP status codes:
  * 200 OK
  * 201 Created
  * 404 Not Found

---

## 📮 Testing

You can test APIs using:

* Browser (GET endpoints)
* Postman for full CRUD operations

---

## 🚀 Deployment

* Containerized using Docker
* Backend hosted on Render
* Database hosted on Neon PostgreSQL
* Environment-variable based configuration

---

## 🧠 Key Design Decisions

### 1. DTO vs Model Separation
- **Model** → Represents database structure  
- **DTO** → Used for API request/response  
- Prevents exposing internal DB schema  

---

### 2. JDBC Instead of JPA
- Full control over SQL  
- Better understanding of DB interactions  
- Optimized queries using JSON aggregation  

---

### 3. JSON Aggregation (PostgreSQL)

Used to fetch series with matches in a single query:
```sql
JSON_AGG(JSON_BUILD_OBJECT(...))
```
- Avoids N+1 query problem
- Reduces multiple DB calls into a single optimized query

---

### 4. Transaction Management

```java
@Transactional
```
Ensures:
- Series + matches insertion is atomic
- No partial data is saved in case of failure
