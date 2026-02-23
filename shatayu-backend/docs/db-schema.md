# Database Schema — shatayudb (MySQL)

All tables are auto-managed by Hibernate (`ddl-auto=update`).

## Tables

### patient
| Column       | Type         | Nullable | Notes              |
|-------------|-------------|----------|--------------------|
| PATIENT_ID  | BIGINT (PK) | NO       | Auto-generated     |
| FIRST_NAME  | VARCHAR      | NO       |                    |
| LAST_NAME   | VARCHAR      | YES      |                    |
| MIDDLE_NAME | VARCHAR      | YES      |                    |
| AGE         | BIGINT       | YES      |                    |
| SEX         | VARCHAR      | NO       |                    |
| INITIAL_DATE| DATE         | NO       | Registration date  |
| MOBILE1     | VARCHAR      | YES      |                    |
| MOBILE2     | VARCHAR      | YES      |                    |
| LANDLINE    | VARCHAR      | YES      |                    |
| KNOWN_CASES | VARCHAR      | YES      | Pre-existing conditions |
| HISTORY     | VARCHAR      | YES      | Medical history    |
| ADDRESS     | VARCHAR      | YES      |                    |

### treatment
| Column          | Type           | Nullable | Notes                     |
|----------------|---------------|----------|---------------------------|
| TREATMENT_ID   | BIGINT (PK)   | NO       | Not auto-generated        |
| PATIENT_ID     | BIGINT (FK)   | YES      | References patient        |
| TREATMENT_DATE | DATE           | NO       |                           |
| SIGNS_SYMPTOMS | MEDIUMTEXT     | NO       |                           |
| TONGUE         | VARCHAR        | NO       | Ayurvedic tongue diagnosis|
| PULSE          | VARCHAR        | NO       | Ayurvedic pulse diagnosis |
| RESULTS        | MEDIUMTEXT     | YES      |                           |
| Paid           | VARCHAR        | YES      | Payment status            |
| AMOUNT_PAID    | FLOAT          | YES      |                           |
| PAYMENT_TYPE   | VARCHAR        | YES      |                           |
| BALANCE        | FLOAT          | YES      |                           |

**Relationships**: Has many `TreatmentDrug` (OneToMany, EAGER fetch)

### drug
| Column    | Type         | Nullable | Notes                  |
|----------|-------------|----------|------------------------|
| DRUG_ID  | BIGINT (PK) | NO       | Not auto-generated     |
| DRUG_NAME| VARCHAR      | NO       |                        |
| DRUG_COST| FLOAT        | YES      |                        |
| CONTENTS | VARCHAR      | YES      | Drug ingredients       |
| QUANTITY | BIGINT       | YES      | Stock quantity         |
| DETAILS  | VARCHAR      | YES      |                        |
| INACTIVE | CHAR(1)      | YES      | Soft-delete flag       |

### treatmentdrug (join table)
| Column            | Type         | Nullable | Notes                       |
|------------------|-------------|----------|-----------------------------|
| TREATMENT_DRUG_ID| BIGINT (PK) | NO       | Not auto-generated          |
| TREATMENT_ID     | BIGINT (FK)  | YES      | References treatment        |
| DRUG_ID          | BIGINT (FK)  | YES      | References drug (EAGER)     |
| DURATION         | VARCHAR      | NO       | Dosage duration instruction |
| NOOFDAYS         | INT          | YES      | Number of days              |

### panchkarma
| Column          | Type         | Nullable | Notes                    |
|----------------|-------------|----------|--------------------------|
| PANCHKARMA_ID  | BIGINT (PK) | NO       | Not auto-generated       |
| PANCHKARMA_NAME| VARCHAR      | NO       | Therapy name             |
| COST           | FLOAT        | YES      |                          |
| PROCD          | VARCHAR      | YES      | Procedure description    |
| DISEASES       | VARCHAR      | YES      | Target diseases          |
| DETAILS        | VARCHAR      | YES      |                          |
| NOOFDAYS       | BIGINT       | YES      | Standard duration        |

### patientpanchkarma
| Column          | Type         | Nullable | Notes                    |
|----------------|-------------|----------|--------------------------|
| PATIENT_PANCH_ID| BIGINT (PK)| NO       | Not auto-generated       |
| PANCHKARMA_ID  | BIGINT (FK)  | YES      | References panchkarma (EAGER) |
| PATIENT_ID     | BIGINT       | YES      | References patient       |
| START_DATE     | DATE          | NO       |                          |
| END_DATE       | DATE          | NO       |                          |
| RESULTS        | VARCHAR      | YES      |                          |
| AMOUNT_PAID    | FLOAT        | YES      |                          |
| Paid           | VARCHAR      | YES      | Payment status           |
| PAYMENT_TYPE   | VARCHAR      | YES      |                          |
| BALANCE        | FLOAT        | YES      |                          |

## Entity Relationships
```
Patient (1) ──< Treatment (1) ──< TreatmentDrug (N) >── Drug (1)
Patient (1) ──< PatientPanchkarma (N) >── Panchkarma (1)
```
Note: Patient→Treatment link is via PATIENT_ID column (no JPA @ManyToOne on Treatment side).
