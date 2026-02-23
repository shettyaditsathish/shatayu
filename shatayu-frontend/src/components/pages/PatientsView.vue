<template>
  <div class="w-100">
    <div class="card w-100">
      <h2 class="text-2xl font-bold mb-4">Patient Search</h2>
      <!-- Search Section -->
      <div class="d-flex gap-2 mb-4 w-100">
        <IconField class="w-100">
          <InputIcon class="pi pi-search" />

          <InputText v-model="searchQuery" placeholder="Search" fluid />
        </IconField>

        <Button
          label="Search"
          icon="pi pi-search"
          @click="searchPatients"
          :loading="loading"
        />
        <Button
          label="Clear"
          icon="pi pi-times"
          severity="secondary"
          @click="clearSearch"
          :disabled="!searchQuery && patients.length === 0"
        />
      </div>

      <!-- Loading Indicator -->
      <div v-if="loading" class="flex justify-center py-4">
        <ProgressSpinner style="width: 50px; height: 50px" />
      </div>

      <!-- Error Message -->
      <Message
        v-if="errorMessage"
        severity="error"
        :closable="false"
        class="mb-4"
      >
        {{ errorMessage }}
      </Message>

      <!-- No Results Message -->
      <Message
        v-if="!loading && searched && patients.length === 0"
        severity="info"
        :closable="false"
        class="mb-4"
      >
        No patients found for "{{ lastSearchQuery }}"
      </Message>

      <!-- DataTable -->
      <DataTable
        v-if="patients.length > 0"
        :value="patients"
        tableStyle="w-100"
        scrollHeight="65vh"
        class="mt-4"
      >
        <Column
          field="firstName"
          header="First Name"
          sortable
          style="width: 30%"
        ></Column>
        <Column
          field="lastName"
          header="Last Name"
          sortable
          style="width: 30%"
        ></Column>
        <Column field="age" header="Age" sortable style="width: 15%"></Column>
        <Column header="Actions" style="width: 15%">
          <template #body="slotProps">
            <Button
              icon="pi pi-eye"
              severity="info"
              size="small"
              @click="viewPatient(slotProps.data)"
              v-tooltip.top="'View Details'"
            />
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
import InputText from "primevue/inputtext";
import Button from "primevue/button";
import Message from "primevue/message";
import Dialog from "primevue/dialog";
import ProgressSpinner from "primevue/progressspinner";
import { Api } from "@/api/api";
import apiService from "@/api/apiservice";
import { IconField, InputIcon } from "primevue";

const router = useRouter();

const searchQuery = ref("");
const patients = ref([]);
const loading = ref(false);
const errorMessage = ref("");
const searched = ref(false);
const lastSearchQuery = ref("");
const displayDialog = ref(false);
const selectedPatient = ref(null);

// Replace this with your actual API endpoint
const API_BASE_URL = "http://localhost:8080/api/patients";

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
    // URL encode the search query to handle spaces
    let resp = await apiService.search.searchPatients({
      name: searchQuery.value,
    });
    patients.value = resp.data;
    console.log(resp.data);
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
.patient-search-container {
  padding: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.card {
  background: white;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
</style>
