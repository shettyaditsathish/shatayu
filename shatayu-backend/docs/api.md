# Backend API Endpoints

All endpoints are REST (JSON). CORS allowed from `http://localhost:5173`.

## PatientController

| Method | Path      | Params         | Returns          | Description                    |
|--------|----------|----------------|------------------|--------------------------------|
| GET    | /test    | —              | List\<Patient\>  | Returns ALL patients           |
| GET    | /search  | ?name=string   | List\<Patient\>  | Search by name (smart: single word searches first+last, two words tries both orderings) |

## TreatmentController

| Method | Path          | Params            | Returns                   | Description                              |
|--------|--------------|-------------------|---------------------------|------------------------------------------|
| GET    | /treatments  | ?patientId=Long   | List\<Treatment\>         | Treatments for patient (newest first), includes treatmentDrugs with drug details |
| GET    | /panchkarma  | ?patientId=Long   | List\<PatientPanchkarma\> | Panchkarma sessions for patient (newest first), includes panchkarma details |

## Notes
- No POST/PUT/DELETE endpoints yet — app is currently read-only
- No authentication/authorization
- Patient search supports partial matching (LIKE %name%) and is case-insensitive
