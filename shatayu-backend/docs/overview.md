# Shatayu Backend - Overview

## Tech Stack
- **Framework**: Spring Boot 3.4.2
- **Java**: 17
- **ORM**: Spring Data JPA (Hibernate)
- **Database**: MySQL 8 (`shatayudb` on localhost:3306, user: root)
- **Build**: Maven (wrapper included)
- **Lombok**: 1.18.30 (for @Data, @Builder, etc.)
- **API Docs**: springdoc-openapi 2.2.0 (Swagger UI at `/swagger-ui/index.html`)
- **DDL Strategy**: `hibernate.ddl-auto=update` (auto-creates/updates tables)

## Project Coordinates
- Group: `com.shatayu`, Artifact: `clinicmgmt`
- Package: `com.shatayu.clinicmgmt`
- Runs on: `http://localhost:8080`

## Key Commands
- `./mvnw spring-boot:run` — Start backend
- `./mvnw install` — Build + generate openapi.json to `/Users/aditshetty/Desktop`

## CORS
- Hardcoded `@CrossOrigin(origins = "http://localhost:5173")` on controllers

## Project Structure
```
src/main/java/com/shatayu/clinicmgmt/
├── ClinicmgmtApplication.java      # Main class
├── controller/
│   ├── PatientController.java       # /test, /search
│   └── TreatmentController.java     # /treatments, /panchkarma
├── service/
│   ├── PatientService.java          # Patient search logic
│   ├── TreatmentService.java        # Treatment queries
│   └── PatientPanchkarmaService.java # Panchkarma queries
├── repository/
│   ├── PatientRepository.java       # Custom JPQL search queries
│   ├── TreatmentRepository.java
│   ├── TreatmentDrugRepository.java
│   ├── PanchkarmaRepository.java
│   └── PatientPanchkarmaRepository.java
└── entity/
    ├── Patient.java
    ├── Treatment.java
    ├── Drug.java
    ├── TreatmentDrug.java
    ├── Panchkarma.java
    └── PatientPanchkarma.java
```
