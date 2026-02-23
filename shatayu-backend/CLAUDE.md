# Shatayu Backend

Ayurvedic clinic management backend — Spring Boot 3.4.2, Java 17, MySQL.

## Documentation
Refer to these files before scanning the codebase:
- [Project Overview](./docs/overview.md) — Tech stack, project structure, build commands
- [Database Schema](./docs/db-schema.md) — All 6 tables with columns, types, relationships
- [API Endpoints](./docs/api.md) — REST endpoints, params, responses

## Quick Reference
- **Package**: `com.shatayu.clinicmgmt`
- **Runs on**: `http://localhost:8080`
- **DB**: MySQL `shatayudb` on localhost:3306
- **Build**: `./mvnw spring-boot:run`
- **Entities**: Patient, Treatment, Drug, TreatmentDrug, Panchkarma, PatientPanchkarma
- **DDL**: `hibernate.ddl-auto=update` (Hibernate manages schema)
- **CORS**: Hardcoded `http://localhost:5173`
- **API Docs**: Swagger UI at `/swagger-ui/index.html`
- **Currently read-only** (GET endpoints only), no auth
