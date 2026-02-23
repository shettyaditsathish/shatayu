<template>
  <div class="">
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
            <label for="dob" class="font-semibold">Date of Birth</label>
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
            <label for="telNo" class="font-semibold">Tel No</label>
            <InputText id="telNo" v-model="patient.telNo" class="w-full" />
          </div>
        </div>
        <div class="col-4 md:col-3">
          <div class="field">
            <label for="mobile" class="font-semibold">Mobile</label>
            <InputText id="mobile" v-model="patient.mobile" class="w-full" />
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-6">
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
        <div class="col-6 md:col-6">
          <div class="field">
            <label for="kco" class="font-semibold">K/C/O</label>
            <InputText id="kco" v-model="patient.kco" class="w-full" />
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

      <DataTable
        :value="treatments"
        v-model:selection="selectedTreatment"
        selectionMode="single"
        dataKey="id"
        :paginator="true"
        :rows="5"
        tableStyle="min-width: 50rem"
      >
        <Column field="date" header="Date" style="width: 15%"></Column>
        <Column
          field="signsSymptoms"
          header="Signs and Symptoms"
          style="width: 30%"
        ></Column>
        <Column
          field="rxDuration"
          header="Rx and Duration"
          style="width: 30%"
        ></Column>
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

      <!-- Add Panchkarma treatment fields here if needed -->
    </Panel>

    <!-- Add/Edit Treatment Dialog -->
    <AddTreatmentModal v-model:showModal="showTreatmentDialog" />
  </div>
</template>

<script setup>
import { ref, reactive } from "vue";
import Panel from "primevue/panel";
import InputText from "primevue/inputtext";
import InputNumber from "primevue/inputnumber";
import Dropdown from "primevue/dropdown";
import Calendar from "primevue/calendar";
import Textarea from "primevue/textarea";
import Button from "primevue/button";
import DataTable from "primevue/datatable";
import Column from "primevue/column";
import AddTreatmentModal from "./AddTreatmentModal.vue";
//const toast = useToast();

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

// Treatment data
const treatments = ref([
  {
    id: 1,
    date: "02/12/2023",
    signsSymptoms: "Headache, fever",
    rxDuration: "Paracetamol 500mg - 3 days",
    tongue: "Normal",
    pulse: "72 bpm",
  },
]);

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

// Treatment methods
const addTreatment = () => {
  isEditMode.value = false;
  resetCurrentTreatment();
  showTreatmentDialog.value = true;
};

const editTreatment = () => {
  if (!selectedTreatment.value) {
    toast.add({
      severity: "warn",
      summary: "Warning",
      detail: "Please select a treatment to edit",
      life: 3000,
    });
    return;
  }
  isEditMode.value = true;
  Object.assign(currentTreatment, selectedTreatment.value);
  showTreatmentDialog.value = true;
};

const deleteTreatment = () => {
  if (!selectedTreatment.value) {
    toast.add({
      severity: "warn",
      summary: "Warning",
      detail: "Please select a treatment to delete",
      life: 3000,
    });
    return;
  }
  const index = treatments.value.findIndex(
    (t) => t.id === selectedTreatment.value.id
  );
  if (index > -1) {
    treatments.value.splice(index, 1);
    selectedTreatment.value = null;
    toast.add({
      severity: "success",
      summary: "Success",
      detail: "Treatment deleted successfully",
      life: 3000,
    });
  }
};

const saveTreatment = () => {
  if (isEditMode.value) {
    const index = treatments.value.findIndex(
      (t) => t.id === currentTreatment.id
    );
    if (index > -1) {
      treatments.value[index] = { ...currentTreatment };
    }
    toast.add({
      severity: "success",
      summary: "Success",
      detail: "Treatment updated successfully",
      life: 3000,
    });
  } else {
    const newTreatment = {
      ...currentTreatment,
      id: treatments.value.length + 1,
    };
    treatments.value.push(newTreatment);
    toast.add({
      severity: "success",
      summary: "Success",
      detail: "Treatment added successfully",
      life: 3000,
    });
  }
  showTreatmentDialog.value = false;
  resetCurrentTreatment();
};

const resetCurrentTreatment = () => {
  currentTreatment.id = null;
  currentTreatment.date = null;
  currentTreatment.signsSymptoms = "";
  currentTreatment.rxDuration = "";
  currentTreatment.tongue = "";
  currentTreatment.pulse = "";
};

const openBilling = () => {
  toast.add({
    severity: "info",
    summary: "Billing",
    detail: "Opening billing module...",
    life: 3000,
  });
};

const savePatient = () => {
  toast.add({
    severity: "success",
    summary: "Success",
    detail: "Patient details saved successfully",
    life: 3000,
  });
  console.log("Patient data:", patient);
  console.log("Treatments:", treatments.value);
};
</script>

<style scoped>
.patient-treatment-container {
  padding: 1.5rem;
  max-width: 1400px;
  margin: 0 auto;
}

.field {
  margin-bottom: 1rem;
}

.field label {
  display: block;
  margin-bottom: 0.5rem;
}
</style>
