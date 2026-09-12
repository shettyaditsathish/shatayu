<template>
  <div class="patients-page">
    <!-- Page Header Card -->
    <div class="page-card">
      <div class="page-card-header">
        <div>
          <h3>Patients Directory</h3>
          <p v-if="patients.length > 0">{{ patients.length }} patient records found</p>
          <p v-else>Search by patient name to view records</p>
        </div>
        <div class="header-actions">
          <Button
            label="Register New Patient"
            icon="pi pi-user-plus"
            class="btn-accent"
            @click="$router.push('/addPatient')"
          />
        </div>
      </div>

      <!-- Search Section -->
      <div class="search-bar">
        <div class="search-input-wrapper">
          <i class="pi pi-search"></i>
          <input
            v-model="searchQuery"
            type="text"
            placeholder="Search patients by name..."
            @keydown.enter="searchPatients"
          />
        </div>
        <Button
          label="Search"
          icon="pi pi-search"
          class="btn-accent"
          @click="searchPatients"
          :loading="loading"
        />
        <Button
          label="Clear"
          icon="pi pi-times"
          severity="secondary"
          outlined
          @click="clearSearch"
          :disabled="!searchQuery && patients.length === 0"
        />
      </div>

      <!-- Loading Indicator -->
      <div v-if="loading" class="loading-container">
        <ProgressSpinner style="width: 50px; height: 50px" />
      </div>

      <!-- Error Message -->
      <Message
        v-if="errorMessage"
        severity="error"
        :closable="false"
        class="mb-3"
      >
        {{ errorMessage }}
      </Message>

      <!-- No Results Message -->
      <Message
        v-if="!loading && searched && patients.length === 0"
        severity="info"
        :closable="false"
        class="mb-3"
      >
        No patients found for "{{ lastSearchQuery }}"
      </Message>

      <!-- DataTable -->
      <DataTable
        v-if="patients.length > 0"
        :value="patients"
        scrollHeight="60vh"
        :rowHover="true"
        class="patients-table"
        @row-click="onRowClick"
      >
        <Column field="firstName" header="First Name" sortable style="width: 30%">
          <template #body="slotProps">
            <div class="patient-name-cell">
              <div class="patient-avatar">
                {{ (slotProps.data.firstName?.[0] || '') + (slotProps.data.lastName?.[0] || '') }}
              </div>
              <div>
                <span class="patient-name">{{ slotProps.data.firstName }}</span>
              </div>
            </div>
          </template>
        </Column>
        <Column field="lastName" header="Last Name" sortable style="width: 30%">
          <template #body="slotProps">
            <span class="cell-text">{{ slotProps.data.lastName }}</span>
          </template>
        </Column>
        <Column field="age" header="Age" sortable style="width: 15%">
          <template #body="slotProps">
            <span class="cell-muted">{{ slotProps.data.age }} yrs</span>
          </template>
        </Column>
        <Column field="sex" header="Sex" style="width: 15%">
          <template #body="slotProps">
            <span class="cell-badge" v-if="slotProps.data.sex">{{ slotProps.data.sex }}</span>
          </template>
        </Column>
        <Column header="" style="width: 10%">
          <template #body>
            <i class="pi pi-chevron-right cell-arrow"></i>
          </template>
        </Column>
      </DataTable>
    </div>

    <!-- Patient Details Dialog -->
    <Dialog
      v-model:visible="displayDialog"
      :header="`Patient Details - ${selectedPatient?.firstName} ${selectedPatient?.lastName}`"
      :modal="true"
      :style="{ width: '30rem' }"
    >
      <div v-if="selectedPatient" class="flex flex-col gap-3">
        <div class="flex justify-between">
          <span class="font-semibold">ID:</span>
          <span>{{ selectedPatient.id }}</span>
        </div>
        <div class="flex justify-between">
          <span class="font-semibold">First Name:</span>
          <span>{{ selectedPatient.firstName }}</span>
        </div>
        <div class="flex justify-between">
          <span class="font-semibold">Last Name:</span>
          <span>{{ selectedPatient.lastName }}</span>
        </div>
        <div class="flex justify-between">
          <span class="font-semibold">Age:</span>
          <span>{{ selectedPatient.age }}</span>
        </div>
      </div>
    </Dialog>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import DataTable from "primevue/datatable";
import Column from "primevue/column";
import Button from "primevue/button";
import Message from "primevue/message";
import Dialog from "primevue/dialog";
import ProgressSpinner from "primevue/progressspinner";
import apiService from "@/api/apiservice";

const router = useRouter();

const searchQuery = ref("");
const patients = ref([]);
const loading = ref(false);
const errorMessage = ref("");
const searched = ref(false);
const lastSearchQuery = ref("");
const displayDialog = ref(false);
const selectedPatient = ref(null);

const searchPatients = async () => {
  if (!searchQuery.value.trim()) {
    errorMessage.value = "Please enter a search query";
    return;
  }

  loading.value = true;
  errorMessage.value = "";
  searched.value = true;
  lastSearchQuery.value = searchQuery.value;

  try {
    let resp = await apiService.search.searchPatients({
      name: searchQuery.value,
    });
    patients.value = resp.data.content;
    console.log("Patients Loadedf", resp.data);
  } catch (error) {
    errorMessage.value = `Error searching patients: ${error.message}`;
    patients.value = [];
  } finally {
    loading.value = false;
  }
};

const clearSearch = () => {
  searchQuery.value = "";
  patients.value = [];
  errorMessage.value = "";
  searched.value = false;
  lastSearchQuery.value = "";
};

const onRowClick = (event) => {
  viewPatient(event.data);
};

const viewPatient = (patient) => {
  router.push({
    path: "/patientDetails",
    query: {
      patientId: patient.patientId,
      firstName: patient.firstName,
      middleName: patient.middleName,
      lastName: patient.lastName,
      age: patient.age,
      sex: patient.sex,
      initialDate: patient.initialDate,
      landline: patient.landline,
      knownCases: patient.knownCases,
      history: patient.history,
      address: patient.address,
      mobile1: patient.mobile1,
      mobile2: patient.mobile2,
    },
  });
};
</script>

<style scoped>
.patients-page {
  display: flex;
  flex-direction: column;
  gap: 1.25rem;
}

.page-card {
  background: white;
  border-radius: var(--radius-2xl);
  border: 1px solid var(--slate-100);
  box-shadow: var(--shadow-sm);
  overflow: hidden;
}

.page-card-header {
  padding: 1.5rem;
  border-bottom: 1px solid var(--slate-100);
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.page-card-header h3 {
  font-size: 1.25rem;
  font-weight: 700;
  color: var(--slate-900);
  margin: 0;
}

.page-card-header p {
  font-size: 0.8rem;
  color: var(--slate-400);
  margin: 0.25rem 0 0;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.btn-accent {
  background: var(--emerald-600) !important;
  border-color: var(--emerald-600) !important;
  font-weight: 600 !important;
  font-size: 0.8rem !important;
  border-radius: 8px !important;
}

.btn-accent:hover {
  background: var(--emerald-700) !important;
  border-color: var(--emerald-700) !important;
}

/* Search */
.search-bar {
  padding: 1rem 1.5rem;
  display: flex;
  gap: 0.5rem;
  align-items: center;
}

.search-input-wrapper {
  flex: 1;
  position: relative;
}

.search-input-wrapper i {
  position: absolute;
  left: 0.75rem;
  top: 50%;
  transform: translateY(-50%);
  color: var(--slate-400);
  font-size: 0.875rem;
}

.search-input-wrapper input {
  width: 100%;
  padding: 0.5rem 1rem 0.5rem 2.25rem;
  background: var(--slate-50);
  border: 1px solid var(--slate-200);
  border-radius: 8px;
  font-size: 0.85rem;
  color: var(--slate-700);
  outline: none;
  transition: all 0.2s;
}

.search-input-wrapper input:focus {
  border-color: var(--emerald-500);
  background: white;
  box-shadow: 0 0 0 3px rgba(5, 150, 105, 0.1);
}

.search-input-wrapper input::placeholder {
  color: var(--slate-400);
}

/* Loading */
.loading-container {
  display: flex;
  justify-content: center;
  padding: 2rem;
}

/* Table cell styles */
.patient-name-cell {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.patient-avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background: var(--emerald-50);
  color: var(--emerald-700);
  font-weight: 700;
  font-size: 0.7rem;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.patient-name {
  font-weight: 600;
  color: var(--slate-900);
}

.cell-text {
  color: var(--slate-700);
  font-weight: 500;
}

.cell-muted {
  color: var(--slate-500);
  font-size: 0.8rem;
}

.cell-badge {
  display: inline-block;
  padding: 0.25rem 0.625rem;
  border-radius: 999px;
  font-size: 0.7rem;
  font-weight: 600;
  background: var(--slate-100);
  color: var(--slate-600);
}

.cell-arrow {
  color: var(--slate-300);
  font-size: 0.75rem;
}

.patients-table {
  cursor: pointer;
}

.mb-3 {
  margin-bottom: 0.75rem;
  margin-left: 1.5rem;
  margin-right: 1.5rem;
}
</style>
