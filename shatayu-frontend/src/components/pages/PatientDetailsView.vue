<template>
  <div class="patient-details-page">
    <!-- Back Button -->
    <div class="back-row">
      <button class="back-btn" @click="goBack">
        <i class="pi pi-arrow-left"></i>
        <span>Back to Patients</span>
      </button>
    </div>

    <!-- Patient Basic Info Section -->
    <Panel header="Patient Information" class="mb-4">
      <div class="row">
        <div class="col-4 md:col-4">
          <div class="field">
            <label for="firstName" class="field-label">First Name</label>
            <InputText
              id="firstName"
              v-model="patient.firstName"
              class="w-full"
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
            />
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-2 md:col-2">
          <div class="field">
            <label for="age" class="field-label">Age</label>
            <InputNumber id="age" v-model="patient.age" class="w-full" />
          </div>
        </div>
        <div class="col-2 md:col-2">
          <div class="field">
            <label for="sex" class="field-label">Sex</label>
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
            <label for="initialDate" class="field-label">Initial Date</label>
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
            <label for="landline" class="field-label">Landline</label>
            <InputText
              id="landline"
              v-model="patient.landline"
              class="w-full"
            />
          </div>
        </div>
        <div class="col-4 md:col-3">
          <div class="field">
            <label for="mobile1" class="field-label">Mobile 1</label>
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
            <label for="mobile2" class="field-label">Mobile 2</label>
            <InputText
              id="mobile2"
              v-model="patient.mobile2"
              class="w-full"
            />
          </div>
        </div>
        <div class="col-3 md:col-3">
          <div class="field">
            <label for="knownCases" class="field-label">K/C/O</label>
            <InputText
              id="knownCases"
              v-model="patient.knownCases"
              class="w-full"
            />
          </div>
        </div>
        <div class="col-6 md:col-6">
          <div class="field">
            <label for="address" class="field-label">Address</label>
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
            <label for="history" class="field-label">History</label>
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

      <div class="mt-3">
        <Button
          label="Save Patient Details"
          icon="pi pi-save"
          class="btn-accent"
          :loading="savingPatient"
          @click="savePatient"
        />
      </div>
    </Panel>

    <!-- Accordions for Treatment and Panchkarma -->
    <Accordion :multiple="true" :activeIndex="[0]">
      <!-- Treatment Accordion -->
      <AccordionTab header="Treatment">
        <div class="section-actions">
          <Button
            label="Add Treatment"
            icon="pi pi-plus"
            class="btn-accent"
            @click="openNewTreatment"
          />
        </div>

        <ProgressSpinner v-if="loadingTreatments && treatments.length === 0" style="width: 50px; height: 50px" />

        <div ref="treatmentScrollContainer" class="scroll-container" @scroll="onTreatmentScroll">
          <DataTable
            :value="treatments"
            selectionMode="single"
            dataKey="treatmentId"
            class="mb-3"
            @row-click="onTreatmentRowClick"
            :rowHover="true"
            style="cursor: pointer"
          >
            <Column field="treatmentDate" header="Date" style="width: 15%">
              <template #body="slotProps">
                {{ formatDate(slotProps.data.treatmentDate) }}
              </template>
            </Column>
            <Column field="signsSymptoms" header="Signs & Symptoms" style="width: 20%" />
            <Column header="Rx and Duration" style="width: 25%">
              <template #body="slotProps">
                <div v-if="slotProps.data.treatmentDrugs && slotProps.data.treatmentDrugs.length" class="rx-list">
                  <template
                    v-for="(td, idx) in slotProps.data.treatmentDrugs"
                    :key="idx"
                  >
                    <div class="rx-name-row">
                      <span>{{ td.drug?.drugName || '' }}</span>
                      <span v-if="td.noOfDays" class="rx-qty">x{{ td.noOfDays }}</span>
                    </div>
                    <div v-if="td.duration" class="rx-duration-row">{{ td.duration }}</div>
                  </template>
                </div>
              </template>
            </Column>
            <Column field="tongue" header="Tongue" style="width: 10%" />
            <Column field="pulse" header="Pulse" style="width: 10%" />
            <Column field="paid" header="Paid" style="width: 10%">
              <template #body="slotProps">
                <span :class="['status-badge', slotProps.data.paid === 'Y' ? 'badge-success' : 'badge-warning']">
                  {{ slotProps.data.paid }}
                </span>
              </template>
            </Column>
            <Column field="amountPaid" header="Amount Paid" style="width: 10%" />
            <Column field="balance" header="Balance" style="width: 10%" />
          </DataTable>
          <div v-if="loadingTreatments && treatments.length > 0" class="text-center py-3">
            <ProgressSpinner style="width: 30px; height: 30px" />
          </div>
        </div>
      </AccordionTab>

      <!-- Panchkarma Treatment Accordion -->
      <AccordionTab header="Panchkarma Treatment">
        <div class="section-actions">
          <Button
            label="Add Panchkarma Treatment"
            icon="pi pi-plus"
            class="btn-accent"
            @click="openNewPanchkarma"
          />
          <Button
            label="Edit"
            icon="pi pi-pencil"
            severity="warning"
            outlined
            :disabled="!selectedPanchkarma"
            @click="openEditPanchkarma"
          />
          <Button
            label="Delete"
            icon="pi pi-trash"
            severity="danger"
            outlined
            :disabled="!selectedPanchkarma"
            @click="deletePanchkarma"
          />
        </div>

        <ProgressSpinner v-if="loadingPanchkarma && panchkarmaList.length === 0" style="width: 50px; height: 50px" />

        <div ref="panchkarmaScrollContainer" class="scroll-container" @scroll="onPanchkarmaScroll">
          <DataTable
            :value="panchkarmaList"
            v-model:selection="selectedPanchkarma"
            selectionMode="single"
            dataKey="patientPanchId"
            :rowHover="true"
          >
            <Column field="panchkarmaName" header="Panchkarma Name" style="width: 40%">
              <template #body="slotProps">
                {{ slotProps.data.panchkarma?.panchkarmaName || '' }}
              </template>
            </Column>
            <Column field="startDate" header="Start Date" style="width: 30%">
              <template #body="slotProps">
                {{ formatDate(slotProps.data.startDate) }}
              </template>
            </Column>
            <Column field="endDate" header="End Date" style="width: 30%">
              <template #body="slotProps">
                {{ formatDate(slotProps.data.endDate) }}
              </template>
            </Column>
          </DataTable>
          <div v-if="loadingPanchkarma && panchkarmaList.length > 0" class="text-center py-3">
            <ProgressSpinner style="width: 30px; height: 30px" />
          </div>
        </div>
      </AccordionTab>
    </Accordion>

    <!-- Add/Edit Treatment Modal -->
    <AddTreatmentModal
      v-model:showModal="showTreatmentModal"
      :treatment="selectedTreatment"
      :patientId="patient.patientId"
      @saved="onTreatmentSaved"
    />

    <!-- Add/Edit Panchkarma Modal -->
    <AddPanchkarmaModal
      v-model:showModal="showPanchkarmaModal"
      :panchkarmaData="selectedPanchkarmaForEdit"
      :patientId="patient.patientId"
      @saved="onPanchkarmaSaved"
    />
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
import Accordion from "primevue/accordion";
import AccordionTab from "primevue/accordiontab";
import ProgressSpinner from "primevue/progressspinner";
import apiService from "@/api/apiservice";
import AddTreatmentModal from "@/components/pages/saveviews/AddTreatmentModal.vue";
import AddPanchkarmaModal from "@/components/pages/saveviews/AddPanchkarmaModal.vue";

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

// Treatments list
const treatments = ref([]);
const loadingTreatments = ref(false);
const treatmentPage = ref(0);
const treatmentPageSize = 10;
const hasMoreTreatments = ref(true);
const treatmentScrollContainer = ref(null);

// Treatment modal state
const showTreatmentModal = ref(false);
const selectedTreatment = ref(null);

// Panchkarma state
const panchkarmaList = ref([]);
const loadingPanchkarma = ref(false);
const selectedPanchkarma = ref(null);
const showPanchkarmaModal = ref(false);
const selectedPanchkarmaForEdit = ref(null);
const panchkarmaPage = ref(0);
const panchkarmaPageSize = 10;
const hasMorePanchkarma = ref(true);
const panchkarmaScrollContainer = ref(null);

const goBack = () => {
  router.push("/patients");
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

const loadTreatments = async (reset = false) => {
  if (!patient.patientId) return;
  if (loadingTreatments.value) return;
  if (!reset && !hasMoreTreatments.value) return;

  if (reset) {
    treatmentPage.value = 0;
    treatments.value = [];
    hasMoreTreatments.value = true;
  }

  loadingTreatments.value = true;
  try {
    const resp = await apiService.treatments.getTreatmentsByPatientId({
      patientId: patient.patientId,
      offset: treatmentPage.value,
      limit: treatmentPageSize,
    });
    const page = resp.data;
    const items = page.content || [];
    treatments.value = [...treatments.value, ...items];
    hasMoreTreatments.value = !page.last;
    treatmentPage.value++;
  } catch (error) {
    console.error("Error loading treatments:", error.message);
  } finally {
    loadingTreatments.value = false;
  }
};

const onTreatmentScroll = (event) => {
  const el = event.target;
  if (el.scrollTop + el.clientHeight >= el.scrollHeight - 10) {
    loadTreatments();
  }
};

const formatDate = (dateStr) => {
  if (!dateStr) return "";
  const date = new Date(dateStr);
  return date.toLocaleDateString("en-GB");
};

const openNewTreatment = () => {
  selectedTreatment.value = null;
  showTreatmentModal.value = true;
};

const onTreatmentRowClick = (event) => {
  selectedTreatment.value = event.data;
  showTreatmentModal.value = true;
};

const onTreatmentSaved = () => {
  loadTreatments(true);
};

// Panchkarma methods
const loadPanchkarma = async (reset = false) => {
  if (!patient.patientId) return;
  if (loadingPanchkarma.value) return;
  if (!reset && !hasMorePanchkarma.value) return;

  if (reset) {
    panchkarmaPage.value = 0;
    panchkarmaList.value = [];
    hasMorePanchkarma.value = true;
  }

  loadingPanchkarma.value = true;
  try {
    const resp = await apiService.panchkarma.getPanchkarmaByPatientId({
      patientId: patient.patientId,
      offset: panchkarmaPage.value,
      limit: panchkarmaPageSize,
    });
    const page = resp.data;
    const items = page.content || [];
    panchkarmaList.value = [...panchkarmaList.value, ...items];
    hasMorePanchkarma.value = !page.last;
    panchkarmaPage.value++;
  } catch (error) {
    console.error("Error loading panchkarma:", error.message);
  } finally {
    loadingPanchkarma.value = false;
  }
};

const onPanchkarmaScroll = (event) => {
  const el = event.target;
  if (el.scrollTop + el.clientHeight >= el.scrollHeight - 10) {
    loadPanchkarma();
  }
};

const openNewPanchkarma = () => {
  selectedPanchkarmaForEdit.value = null;
  showPanchkarmaModal.value = true;
};

const openEditPanchkarma = () => {
  if (!selectedPanchkarma.value) return;
  selectedPanchkarmaForEdit.value = selectedPanchkarma.value;
  showPanchkarmaModal.value = true;
};

const deletePanchkarma = async () => {
  if (!selectedPanchkarma.value) return;
  if (!confirm("Are you sure you want to delete this panchkarma treatment?")) return;

  try {
    await apiService.instance.delete(
      `/patientPanchkarma/${selectedPanchkarma.value.patientPanchId}`
    );
    selectedPanchkarma.value = null;
    loadPanchkarma(true);
  } catch (error) {
    console.error("Error deleting panchkarma:", error.message);
    alert("Error deleting panchkarma treatment");
  }
};

const onPanchkarmaSaved = () => {
  selectedPanchkarma.value = null;
  loadPanchkarma(true);
};

const savingPatient = ref(false);

const savePatient = async () => {
  savingPatient.value = true;
  try {
    const payload = {
      ...patient,
      initialDate: patient.initialDate ? patient.initialDate.toISOString() : null,
    };
    await apiService.patient.savePatient(payload);
    alert("Patient details saved successfully");
  } catch (error) {
    console.error("Error saving patient:", error.message);
    alert("Error saving patient details");
  } finally {
    savingPatient.value = false;
  }
};

onMounted(() => {
  loadPatientData();
  loadTreatments();
  loadPanchkarma();
});
</script>

<style scoped>
.patient-details-page {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.back-row {
  margin-bottom: 0.25rem;
}

.back-btn {
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.5rem 1rem;
  border: 1px solid var(--slate-200);
  border-radius: 8px;
  background: white;
  color: var(--slate-600);
  font-size: 0.8rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
}

.back-btn:hover {
  background: var(--slate-50);
  border-color: var(--slate-300);
  color: var(--slate-800);
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
}

.scroll-container {
  max-height: 400px;
  overflow-y: auto;
}

.status-badge {
  display: inline-block;
  padding: 0.2rem 0.625rem;
  border-radius: 999px;
  font-size: 0.7rem;
  font-weight: 600;
}

.badge-success {
  background: var(--emerald-50);
  color: var(--emerald-700);
}

.badge-warning {
  background: #fffbeb;
  color: #d97706;
}

.rx-list {
  font-size: 0.8rem;
  line-height: 1.5;
}

.rx-name-row {
  display: flex;
  justify-content: space-between;
  font-weight: 600;
}

.rx-qty {
  font-weight: 400;
  color: var(--slate-500);
  margin-left: 0.5rem;
  white-space: nowrap;
}

.rx-duration-row {
  color: var(--slate-500);
  padding-left: 0.25rem;
  margin-bottom: 0.25rem;
}

.mb-4 {
  margin-bottom: 1rem;
}

.mt-3 {
  margin-top: 0.75rem;
}
</style>
