<template>
  <div class="">
    <!-- Back Button -->
    <div class="mb-3">
      <Button
        label="Back to Patients"
        icon="pi pi-arrow-left"
        severity="secondary"
        @click="goBack"
      />
    </div>

    <!-- Patient Basic Info Section -->
    <Panel header="Patient Information" class="mb-3">
      <div class="row">
        <div class="col-4 md:col-4">
          <div class="field">
            <label for="firstName" class="font-semibold">First Name</label>
            <InputText
              id="firstName"
              v-model="patient.firstName"
              class="w-full"
            />
          </div>
        </div>
        <div class="col-4 md:col-4">
          <div class="field">
            <label for="middleName" class="font-semibold">Middle Name</label>
            <InputText
              id="middleName"
              v-model="patient.middleName"
              class="w-full"
            />
          </div>
        </div>
        <div class="col-4 md:col-4">
          <div class="field">
            <label for="lastName" class="font-semibold">Last Name</label>
            <InputText
              id="lastName"
              v-model="patient.lastName"
              class="w-full"
            />
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-2 md:col-2">
          <div class="field">
            <label for="age" class="font-semibold">Age</label>
            <InputNumber id="age" v-model="patient.age" class="w-full" />
          </div>
        </div>
        <div class="col-2 md:col-2">
          <div class="field">
            <label for="sex" class="font-semibold">Sex</label>
            <Dropdown
              id="sex"
              v-model="patient.sex"
              :options="sexOptions"
              placeholder="Select"
              class="w-full"
            />
          </div>
        </div>
        <div class="col-2 md:col-2">
          <div class="field">
            <label for="initialDate" class="font-semibold">Initial Date</label>
            <Calendar
              id="initialDate"
              v-model="patient.initialDate"
              dateFormat="dd/mm/yy"
              class="w-full"
            />
          </div>
        </div>
        <div class="col-2 md:col-3">
          <div class="field">
            <label for="landline" class="font-semibold">Landline</label>
            <InputText
              id="landline"
              v-model="patient.landline"
              class="w-full"
            />
          </div>
        </div>
        <div class="col-4 md:col-3">
          <div class="field">
            <label for="mobile1" class="font-semibold">Mobile 1</label>
            <InputText
              id="mobile1"
              v-model="patient.mobile1"
              class="w-full"
            />
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-3 md:col-3">
          <div class="field">
            <label for="mobile2" class="font-semibold">Mobile 2</label>
            <InputText
              id="mobile2"
              v-model="patient.mobile2"
              class="w-full"
            />
          </div>
        </div>
        <div class="col-3 md:col-3">
          <div class="field">
            <label for="knownCases" class="font-semibold">K/C/O</label>
            <InputText
              id="knownCases"
              v-model="patient.knownCases"
              class="w-full"
            />
          </div>
        </div>
        <div class="col-6 md:col-6">
          <div class="field">
            <label for="address" class="font-semibold">Address</label>
            <Textarea
              id="address"
              v-model="patient.address"
              rows="2"
              class="w-full"
              fluid
            />
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-12">
          <div class="field">
            <label for="history" class="font-semibold">History</label>
            <Textarea
              id="history"
              v-model="patient.history"
              rows="2"
              class="w-full"
              fluid
            />
          </div>
        </div>
      </div>
    </Panel>

    <!-- Treatment Section -->
    <Panel header="Treatment" class="mb-3">
      <div class="mb-3 flex gap-2">
        <Button
          label="Add Treatment Details"
          icon="pi pi-plus"
          @click="addTreatment"
        />
        <Button
          label="Edit Treatment Details"
          icon="pi pi-pencil"
          severity="warning"
          @click="editTreatment"
        />
        <Button
          label="Delete Treatment"
          icon="pi pi-trash"
          severity="danger"
          @click="deleteTreatment"
        />
        <Button
          label="Billing"
          icon="pi pi-dollar"
          severity="success"
          @click="openBilling"
        />
      </div>

      <!-- Loading Indicator for Treatments -->
      <div v-if="treatmentsLoading" class="flex justify-center py-4">
        <ProgressSpinner style="width: 50px; height: 50px" />
      </div>

      <Message
        v-if="treatmentsError"
        severity="error"
        :closable="false"
        class="mb-4"
      >
        {{ treatmentsError }}
      </Message>

      <DataTable
        v-if="!treatmentsLoading"
        :value="treatments"
        v-model:selection="selectedTreatment"
        selectionMode="single"
        dataKey="treatmentId"
        :paginator="true"
        :rows="5"
        tableStyle="min-width: 50rem"
      >
        <Column field="treatmentDate" header="Date" style="width: 15%">
          <template #body="slotProps">
            {{ formatDate(slotProps.data.treatmentDate) }}
          </template>
        </Column>
        <Column
          field="signsSymptoms"
          header="Signs and Symptoms"
          style="width: 30%"
        ></Column>
        <Column header="Rx and Duration" style="width: 30%">
          <template #body="slotProps">
            {{ formatRxDuration(slotProps.data.treatmentDrugs) }}
          </template>
        </Column>
        <Column field="tongue" header="Tongue" style="width: 12.5%"></Column>
        <Column field="pulse" header="Pulse" style="width: 12.5%"></Column>
      </DataTable>
    </Panel>

    <!-- Panchkarma Treatment Section -->
    <Panel header="Panchkarma Treatment" class="mb-3">
      <div class="mb-3">
        <Button
          label="Save Patient Details"
          icon="pi pi-save"
          severity="success"
          @click="savePatient"
        />
      </div>
    </Panel>

    <!-- Add/Edit Treatment Dialog -->
    <AddTreatmentModal v-model:showModal="showTreatmentDialog" />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import Panel from "primevue/panel";
import InputText from "primevue/inputtext";
import InputNumber from "primevue/inputnumber";
import Dropdown from "primevue/dropdown";
import Calendar from "primevue/calendar";
import Textarea from "primevue/textarea";
import Button from "primevue/button";
import DataTable from "primevue/datatable";
import Column from "primevue/column";
import Message from "primevue/message";
import ProgressSpinner from "primevue/progressspinner";
import AddTreatmentModal from "./saveviews/AddTreatmentModal.vue";
import apiService from "@/api/apiservice";

const router = useRouter();
const route = useRoute();

// Patient data
const patient = reactive({
  patientId: null,
  firstName: "",
  middleName: "",
  lastName: "",
  age: null,
  sex: null,
  initialDate: null,
  landline: "",
  knownCases: "",
  history: "",
  address: "",
  mobile1: "",
  mobile2: "",
});

// Dropdown options
const sexOptions = ref(["Male", "Female", "Other"]);

// Treatment data
const treatments = ref([]);
const selectedTreatment = ref(null);
const showTreatmentDialog = ref(false);
const treatmentsLoading = ref(false);
const treatmentsError = ref("");

const goBack = () => {
  router.push("/patients");
};

const formatDate = (dateStr) => {
  if (!dateStr) return "";
  const date = new Date(dateStr);
  return date.toLocaleDateString("en-GB");
};

const formatRxDuration = (treatmentDrugs) => {
  if (!treatmentDrugs || treatmentDrugs.length === 0) return "";
  return treatmentDrugs
    .map((td) => {
      const drugName = td.drug?.drugName || "";
      const duration = td.duration || "";
      const days = td.noOfDays ? `${td.noOfDays}d` : "";
      return `${drugName} ${duration} ${days}`.trim();
    })
    .join(", ");
};

const loadPatientData = () => {
  const patientData = route.query;
  if (patientData.patientId) {
    patient.patientId = Number(patientData.patientId);
    patient.firstName = patientData.firstName || "";
    patient.middleName = patientData.middleName || "";
    patient.lastName = patientData.lastName || "";
    patient.age = patientData.age ? Number(patientData.age) : null;
    patient.sex = patientData.sex || null;
    patient.initialDate = patientData.initialDate
      ? new Date(patientData.initialDate)
      : null;
    patient.landline = patientData.landline || "";
    patient.knownCases = patientData.knownCases || "";
    patient.history = patientData.history || "";
    patient.address = patientData.address || "";
    patient.mobile1 = patientData.mobile1 || "";
    patient.mobile2 = patientData.mobile2 || "";
  }
};

const loadTreatments = async () => {
  if (!patient.patientId) return;

  treatmentsLoading.value = true;
  treatmentsError.value = "";

  try {
    const resp = await apiService.treatments.getTreatmentsByPatientId({
      patientId: patient.patientId,
    });
    treatments.value = resp.data;
  } catch (error) {
    treatmentsError.value = `Error loading treatments: ${error.message}`;
  } finally {
    treatmentsLoading.value = false;
  }
};

// Treatment methods
const addTreatment = () => {
  showTreatmentDialog.value = true;
};

const editTreatment = () => {
  if (!selectedTreatment.value) {
    alert("Please select a treatment to edit");
    return;
  }
  showTreatmentDialog.value = true;
};

const deleteTreatment = () => {
  if (!selectedTreatment.value) {
    alert("Please select a treatment to delete");
    return;
  }
  const index = treatments.value.findIndex(
    (t) => t.treatmentId === selectedTreatment.value.treatmentId
  );
  if (index > -1) {
    treatments.value.splice(index, 1);
    selectedTreatment.value = null;
  }
};

const openBilling = () => {
  alert("Opening billing module...");
};

const savePatient = () => {
  console.log("Patient data:", patient);
  console.log("Treatments:", treatments.value);
  alert("Patient details saved successfully");
};

onMounted(() => {
  loadPatientData();
  loadTreatments();
});
</script>

<style scoped>
.field {
  margin-bottom: 1rem;
}

.field label {
  display: block;
  margin-bottom: 0.5rem;
}
</style>
