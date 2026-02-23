# Shatayu Frontend

Ayurvedic clinic management app — Vue 3 frontend.

## Key Commands
- `npm run dev` — Start dev server (port 5173)
- `npm run build` — Production build
- `npm run sta` — Regenerate API client from `openapi.json` via swagger-typescript-api

## Tech Stack
- **Vue 3** (Composition API, `<script setup>`)
- **Vite 7** (dev server & build)
- **PrimeVue 4** with Aura theme (dark mode disabled, forced light)
- **Bootstrap 5** (grid system: row/col classes used alongside PrimeVue)
- **Axios** (HTTP client, used by generated API)
- **vue-router 4** with memory history

## Project Structure

```
src/
  main.js                    — App entry, router setup, PrimeVue config
  App.vue                    — Root layout: HeaderView (7%) + SideBar (col-2) + MainView (col-10)
  api/
    api.ts                   — Auto-generated API client (swagger-typescript-api, axios-based)
    apiservice.ts            — Singleton Api instance, baseURL http://localhost:8080
  components/
    HeaderView.vue           — Top navbar with "Shatayu" branding, sign out button
    SideBar.vue              — Left nav: Dashboard, Patients, Add Patient, Panchakarma
    MainView.vue             — <RouterView /> wrapper inside a card
    pages/
      DashboardView.vue      — Placeholder "Dashboard View"
      PatientsView.vue       — Patient search (by name), results table, action button navigates to details
      PatientDetailsView.vue — View/edit patient details + treatments (loaded from API)
      PanchkarmaView.vue     — Placeholder "Panchkarma View"
      saveviews/
        AddPatientView.vue   — Add new patient form (Patient Info panel + Treatment table + Panchkarma panel)
        AddTreatmentModal.vue — Dialog for adding treatment with drug table, amounts, paid status
```

## Routing (src/main.js)

Uses `createMemoryHistory()` (in-memory, no URL changes in browser).

| Path | Component | Description |
|------|-----------|-------------|
| `/` | DashboardView | Dashboard (placeholder) |
| `/patients` | PatientsView | Patient search & results table |
| `/addPatient` | AddPatientView | New patient form |
| `/patientDetails` | PatientDetailsView | View/edit patient (receives patient data via query params) |
| `/panchkarma` | PanchkarmaView | Panchkarma (placeholder) |

### Navigation Patterns
- PatientsView -> PatientDetailsView: passes full patient object as query params via `router.push()`
- PatientDetailsView -> PatientsView: "Back to Patients" button via `router.push("/patients")`

## Entities (defined in src/api/api.ts)

- **Patient**: patientId, firstName, lastName, middleName, age, sex, initialDate, landline, knownCases, history, address, mobile1, mobile2
- **Treatment**: treatmentId, patientId, treatmentDate, signsSymptoms, tongue, pulse, results, paid, amountPaid, paymentType, balance, treatmentDrugs[]
- **TreatmentDrug**: treatmentDrugId, drug (Drug), duration (string), noOfDays
- **Drug**: drugId, drugName, drugCost, contents, quantity, details, inactive
- **PatientPanchkarma**: patientPanchId, panchkarma, patientId, startDate, endDate, results, amountPaid, paid, paymentType, balance
- **Panchkarma**: panchkarmaId, panchkarmaName, cost, procd, diseases, details, noOfDays

## API Endpoints (src/api/api.ts)

| Method | Path | API method | Description |
|--------|------|------------|-------------|
| GET | /test | `test.helloWorld()` | Returns Patient[] |
| GET | /search?name= | `search.searchPatients({name})` | Search patients by name |
| GET | /treatments?patientId= | `treatments.getTreatmentsByPatientId({patientId})` | Get treatments for a patient |
| GET | /panchkarma?patientId= | *(not yet in client)* | Get panchkarma for a patient |

### Usage
```js
import apiService from "@/api/apiservice";
const resp = await apiService.search.searchPatients({ name: "John" });
const resp = await apiService.treatments.getTreatmentsByPatientId({ patientId: 123 });
```

## UI Patterns
- Layout: Bootstrap grid (row/col) for form layouts inside PrimeVue Panel components
- Forms: PrimeVue InputText, InputNumber, Dropdown, Calendar, Textarea
- Tables: PrimeVue DataTable + Column
- Dialogs: PrimeVue Dialog
- Icons: PrimeIcons (`pi pi-*`)
- Loading: PrimeVue ProgressSpinner
- Messages: PrimeVue Message component

## Code Style
- Components use `<script setup>` (Composition API)
- Most files use JS (not TS), despite .ts API files
- `ref()` for simple state, `reactive()` for objects
- API client imported as `apiService` from `@/api/apiservice`
- No state management library (Pinia/Vuex) — local component state only

## Important Notes
- `api.ts` is auto-generated — manual edits will be overwritten by `npm run sta`
- Backend base URL hardcoded: `http://localhost:8080`
- Memory history means browser URL doesn't change — no deep linking support
- Backend: Spring Boot 3.4.2 + Java 17 + MySQL (`shatayudb` on localhost:3306)
