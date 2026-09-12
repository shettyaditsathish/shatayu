<template>
  <div class="add-patient-page">
    <!-- Patient Basic Info Section -->
    <Panel header="New Patient Enrollment" class="mb-4">
      <div class="row">
        <div class="col-4 md:col-4">
          <div class="field">
            <label for="firstName" class="field-label">First Name</label>
            <InputText
              id="firstName"
              v-model="patient.firstName"
              class="w-full"
              placeholder="e.g., Sunil"
            />
          </div>
        </div>
        <div class="col-4 md:col-4">
          <div class="field">
            <label for="middleName" class="field-label">Middle Name</label>
            <InputText
              id="middleName"
              v-model="patient.middleName"
              class="w-full"
              placeholder="e.g., Kumar"
            />
          </div>
        </div>
        <div class="col-4 md:col-4">
          <div class="field">
            <label for="lastName" class="field-label">Last Name</label>
            <InputText
              id="lastName"
              v-model="patient.lastName"
              class="w-full"
              placeholder="e.g., Varma"
            />
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-2 md:col-2">
          <div class="field">
            <label for="age" class="field-label">Age</label>
            <InputNumber
              id="age"
              v-model="patient.age"
              class="w-full"
              placeholder="e.g., 35"
            />
          </div>
        </div>
        <div class="col-2 md:col-2">
          <div class="field">
            <label for="sex" class="field-label">Gender</label>
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
            <label for="dob" class="field-label">Date of Birth</label>
            <Calendar
              id="dob"
              v-model="patient.dob"
              dateFormat="dd/mm/yy"
              class="w-full"
            />
          </div>
        </div>
        <div class="col-2 md:col-3">
          <div class="field">
            <label for="telNo" class="field-label">Tel No</label>
            <InputText
              id="telNo"
              v-model="patient.telNo"
              class="w-full"
              placeholder="Landline number"
            />
          </div>
        </div>
        <div class="col-4 md:col-3">
          <div class="field">
            <label for="mobile" class="field-label">Mobile</label>
            <InputText
              id="mobile"
              v-model="patient.mobile"
              class="w-full"
              placeholder="e.g., 99482 12345"
            />
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-6">
          <div class="field">
            <label for="address" class="field-label">Address</label>
            <Textarea
              id="address"
              v-model="patient.address"
              rows="2"
              class="w-full"
              fluid
              placeholder="Complete physical address..."
            />
          </div>
        </div>
        <div class="col-6 md:col-6">
          <div class="field">
            <label for="kco" class="field-label">K/C/O</label>
            <InputText
              id="kco"
              v-model="patient.kco"
              class="w-full"
              placeholder="Known case of..."
            />
          </div>
        </div>
      </div>
    </Panel>

    <div class="section-actions">
      <Button
        label="Save Patient Details"
        icon="pi pi-save"
        class="btn-accent"
        :loading="savingPatient"
        @click="savePatient"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from "vue";
import apiService from "@/api/apiservice";
import Panel from "primevue/panel";
import InputText from "primevue/inputtext";
import InputNumber from "primevue/inputnumber";
import Dropdown from "primevue/dropdown";
import Calendar from "primevue/calendar";
import Textarea from "primevue/textarea";
import Button from "primevue/button";

// Patient data
const patient = reactive({
  firstName: "",
  middleName: "",
  lastName: "",
  age: null,
  sex: null,
  dob: null,
  telNo: "",
  mobile: "",
  address: "",
  kco: "",
});

// Dropdown options
const sexOptions = ref(["Male", "Female", "Other"]);

const selectedTreatment = ref(null);
const showTreatmentDialog = ref(false);
const isEditMode = ref(false);

const currentTreatment = reactive({
  id: null,
  date: null,
  signsSymptoms: "",
  rxDuration: "",
  tongue: "",
  pulse: "",
});

const savingPatient = ref(false);

const savePatient = async () => {
  savingPatient.value = true;
  try {
    const payload = {
      firstName: patient.firstName,
      middleName: patient.middleName,
      lastName: patient.lastName,
      age: patient.age,
      sex: patient.sex,
      initialDate: patient.dob ? patient.dob.toISOString() : null,
      landline: patient.telNo,
      mobile1: patient.mobile,
      address: patient.address,
      knownCases: patient.kco,
    };
    await apiService.patient.savePatient(payload);
    alert("Patient saved successfully");
  } catch (error) {
    console.error("Error saving patient:", error.message);
    alert("Error saving patient");
  } finally {
    savingPatient.value = false;
  }
};
</script>

<style scoped>
.add-patient-page {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.field {
  margin-bottom: 1rem;
}

.field-label {
  display: block;
  margin-bottom: 0.375rem;
  font-size: 0.75rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.03em;
  color: var(--slate-500);
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

.section-actions {
  margin-bottom: 1rem;
  display: flex;
  gap: 0.5rem;
  flex-wrap: wrap;
}

.mb-4 {
  margin-bottom: 1rem;
}
</style>
