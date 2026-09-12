<template>
  <div>
    <Dialog
      :header="treatment ? 'Edit Treatment' : 'Add Treatment'"
      v-model:visible="visibleModel"
      :style="{ width: '70vw' }"
      :modal="true"
    >
      <div class="p-fluid">
        <!-- Treatment Date -->
        <div class="row mb-3">
          <div class="col-3">
            <label for="treatmentDate" class="field-label"
              >Treatment Date</label
            >
            <DatePicker v-model="formData.treatmentDate" class="w-full" />
          </div>
        </div>

        <!-- Signs and Symptoms -->
        <div class="row mb-3">
          <div class="col-12">
            <label for="signsSymptoms" class="field-label"
              >Signs and Symptoms</label
            >
            <Textarea
              id="signsSymptoms"
              v-model="formData.signsSymptoms"
              rows="2"
              class="w-full"
              fluid
            />
          </div>
        </div>

        <!-- Tongue / Pulse -->
        <div class="row mb-3">
          <div class="col-6">
            <label for="tongue" class="field-label">Tongue</label>
            <Textarea
              id="tongue"
              v-model="formData.tongue"
              rows="2"
              class="w-full"
              fluid
            />
          </div>
          <div class="col-6">
            <label for="pulse" class="field-label">Pulse</label>
            <Textarea
              id="pulse"
              v-model="formData.pulse"
              rows="2"
              class="w-full"
              fluid
            />
          </div>
        </div>

        <!-- Investigations -->
        <div class="row mb-3">
          <div class="col-12">
            <label for="investigations" class="field-label"
              >Investigations</label
            >
            <Textarea
              id="investigations"
              v-model="formData.investigations"
              rows="2"
              class="w-full"
              fluid
            />
          </div>
        </div>

        <!-- Result/Remark -->
        <div class="row mb-3">
          <div class="col-12">
            <label for="results" class="field-label">Result/Remark</label>
            <Textarea
              id="results"
              v-model="formData.results"
              rows="2"
              class="w-full"
              fluid
            />
          </div>
        </div>

        <!-- Amount Section -->
        <div class="row mb-3">
          <div class="col-4">
            <label for="totalAmount" class="field-label">Total Amount</label>
            <div class="d-flex gap-2">
              <InputNumber
                id="totalAmount"
                v-model="formData.totalAmount"
                class="w-full"
              />
              <Button
                label="calc"
                severity="secondary"
                outlined
                @click="calculateBalance"
              />
            </div>
          </div>
          <div class="col-4">
            <label for="amountPaid" class="field-label">Amount Paid</label>
            <InputNumber
              id="amountPaid"
              v-model="formData.amountPaid"
              class="w-full"
              @input="calculateBalance"
            />
          </div>
          <div class="col-4">
            <label for="balance" class="field-label">Balance</label>
            <InputNumber
              id="balance"
              v-model="formData.balance"
              class="w-full"
              :disabled="true"
            />
          </div>
        </div>

        <!-- Paid Status -->
        <div class="row mb-3">
          <div class="col-3">
            <label for="paidStatus" class="field-label">Paid Status</label>
            <Select
              id="paidStatus"
              v-model="formData.paidStatus"
              :options="paidStatusOptions"
              placeholder="Select Status"
              class="w-full"
            />
          </div>
        </div>

        <!-- Add Row / Remove Row Buttons -->
        <div class="section-actions mb-3">
          <Button
            label="Add Row"
            icon="pi pi-plus"
            class="btn-accent"
            @click="addRow"
          />
          <Button
            label="Remove Row"
            icon="pi pi-minus"
            severity="danger"
            outlined
            @click="removeRow"
          />
        </div>

        <!-- Drug Table -->
        <DataTable
          :value="tableData"
          v-model:selection="selectedRow"
          selectionMode="single"
          dataKey="id"
          class="mb-3"
        >
          <Column field="drugName" header="Medicine" style="width: 35%">
            <template #body="slotProps">
              <Select
                v-if="slotProps.data.isEditing"
                v-model="slotProps.data.drug"
                :options="drugs"
                optionLabel="drugName"
                filter
                filterPlaceholder="Search drug..."
                placeholder="Select drug"
                class="w-full"
              />
              <span v-else class="drug-name">{{
                slotProps.data.drug?.drugName || ""
              }}</span>
            </template>
          </Column>
          <Column field="duration" header="Duration" style="width: 25%">
            <template #body="slotProps">
              <div
                v-if="slotProps.data.isEditing"
                class="d-flex align-items-center gap-1"
              >
                <InputNumber
                  v-model="slotProps.data.durationMorning"
                  :min="0"
                  :max="9"
                  :input-style="{ width: '60px' }"
                />
                <span> - </span>
                <InputNumber
                  v-model="slotProps.data.durationAfternoon"
                  :min="0"
                  :max="9"
                  :input-style="{ width: '60px' }"
                />
                <span> - </span>
                <InputNumber
                  v-model="slotProps.data.durationNight"
                  :min="0"
                  :max="9"
                  :input-style="{ width: '60px' }"
                />
              </div>
              <span v-else class="cell-mono">{{
                formatDuration(slotProps.data)
              }}</span>
            </template>
          </Column>
          <Column field="noOfDays" header="No of Days" style="width: 20%">
            <template #body="slotProps">
              <InputNumber
                v-if="slotProps.data.isEditing"
                v-model="slotProps.data.noOfDays"
                :min="1"
                class="w-full"
              />
              <span v-else>{{ slotProps.data.noOfDays }}</span>
            </template>
          </Column>
          <Column header="" style="width: 20%">
            <template #body="slotProps">
              <div class="d-flex gap-2">
                <Button
                  v-if="slotProps.data.isEditing"
                  icon="pi pi-check"
                  severity="success"
                  size="small"
                  text
                  v-tooltip.top="'Save'"
                  @click="saveDrugRow(slotProps.data)"
                />
                <Button
                  v-else
                  icon="pi pi-pencil"
                  severity="warning"
                  size="small"
                  text
                  v-tooltip.top="'Edit'"
                  @click="editDrugRow(slotProps.data)"
                />
                <Button
                  icon="pi pi-trash"
                  severity="danger"
                  size="small"
                  text
                  v-tooltip.top="'Delete'"
                  @click="deleteDrugRow(slotProps.data)"
                />
              </div>
            </template>
          </Column>
        </DataTable>

        <!-- Generate Invoice -->
        <div class="mt-3">
          <Button
            label="Generate Invoice"
            icon="pi pi-file"
            severity="secondary"
            outlined
            @click="generateInvoice"
          />
        </div>
      </div>

      <template #footer>
        <Button
          label="Cancel"
          icon="pi pi-times"
          text
          @click="visibleModel = false"
        />
        <Button
          label="Submit Data"
          icon="pi pi-check"
          class="btn-accent"
          @click="submitData"
        />
      </template>
    </Dialog>
  </div>
</template>

<script setup>
import { ref, reactive, watch, onMounted } from "vue";
import {
  Button,
  Column,
  DataTable,
  DatePicker,
  Dialog,
  InputNumber,
  Select,
  Textarea,
} from "primevue";
import apiService from "@/api/apiservice";

const props = defineProps({
  treatment: {
    type: Object,
    default: null,
  },
  patientId: {
    type: Number,
    default: null,
  },
});

const emit = defineEmits(["saved"]);

let visibleModel = defineModel("showModal", {
  type: Boolean,
  required: true,
});

const selectedRow = ref(null);
let nextId = 1;

const drugs = ref([]);

onMounted(async () => {
  try {
    const resp = await apiService.drugs.getActiveDrugs();
    drugs.value = resp.data;
  } catch (error) {
    console.error("Error loading drugs:", error.message);
  }
});

const formData = reactive({
  treatmentDate: new Date(),
  signsSymptoms: "",
  tongue: "",
  pulse: "",
  investigations: "",
  results: "",
  totalAmount: 0,
  amountPaid: 0,
  balance: 0,
  paidStatus: null,
});

const paidStatusOptions = ["Y", "N"];
const tableData = ref([]);

// Watch for treatment prop changes to populate form for editing
watch(
  () => props.treatment,
  (newTreatment) => {
    if (newTreatment) {
      formData.treatmentDate = newTreatment.treatmentDate
        ? new Date(newTreatment.treatmentDate)
        : new Date();
      formData.signsSymptoms = newTreatment.signsSymptoms || "";
      formData.tongue = newTreatment.tongue || "";
      formData.pulse = newTreatment.pulse || "";
      formData.results = newTreatment.results || "";
      formData.amountPaid = newTreatment.amountPaid || 0;
      formData.balance = newTreatment.balance || 0;
      formData.paidStatus = newTreatment.paid || null;
      formData.totalAmount =
        (newTreatment.amountPaid || 0) + (newTreatment.balance || 0);
      formData.investigations = "";

      if (
        newTreatment.treatmentDrugs &&
        newTreatment.treatmentDrugs.length > 0
      ) {
        tableData.value = newTreatment.treatmentDrugs.map((td) => {
          const dur = parseDuration(td.duration);
          return {
            id: nextId++,
            drug: td.drug || null,
            durationMorning: dur.morning,
            durationAfternoon: dur.afternoon,
            durationNight: dur.night,
            noOfDays: td.noOfDays || 1,
            isEditing: false,
          };
        });
      } else {
        tableData.value = [];
      }
    } else {
      resetForm();
    }
  },
);

const parseDuration = (durationStr) => {
  if (!durationStr) return { morning: 0, afternoon: 0, night: 0 };
  const parts = durationStr.split("-").map(Number);
  return {
    morning: parts[0] || 0,
    afternoon: parts[1] || 0,
    night: parts[2] || 0,
  };
};

const formatDuration = (row) => {
  return `${row.durationMorning || 0}-${row.durationAfternoon || 0}-${row.durationNight || 0}`;
};

const calculateBalance = () => {
  formData.balance = formData.totalAmount - formData.amountPaid;
};

const addRow = () => {
  tableData.value.push({
    id: nextId++,
    drug: null,
    durationMorning: 1,
    durationAfternoon: 1,
    durationNight: 1,
    noOfDays: 1,
    isEditing: true,
  });
};

const removeRow = () => {
  if (selectedRow.value) {
    const index = tableData.value.findIndex(
      (row) => row.id === selectedRow.value.id,
    );
    if (index > -1) {
      tableData.value.splice(index, 1);
      selectedRow.value = null;
    }
  } else {
    alert("Please select a row to remove");
  }
};

const editDrugRow = (row) => {
  row.isEditing = true;
};

const saveDrugRow = (row) => {
  row.isEditing = false;
};

const deleteDrugRow = (row) => {
  const index = tableData.value.findIndex((r) => r.id === row.id);
  if (index > -1) {
    tableData.value.splice(index, 1);
    if (selectedRow.value?.id === row.id) {
      selectedRow.value = null;
    }
  }
};

const generateInvoice = () => {
  console.log("Treatment form:", formData);
  console.log("Drugs:", tableData.value);
  alert("Invoice generated!");
};

const submitData = () => {
  if (!formData.treatmentDate) {
    alert("Please select a treatment date");
    return;
  }

  tableData.value.forEach((row) => {
    row.isEditing = false;
  });

  const submitPayload = {
    ...formData,
    patientId: props.patientId,
    prescriptions: tableData.value.map((row) => ({
      drugName: row.drugName,
      duration: formatDuration(row),
      noOfDays: row.noOfDays,
    })),
  };

  console.log("Submitting data:", submitPayload);
  alert("Data submitted successfully!");
  visibleModel.value = false;
  emit("saved");
  resetForm();
};

const resetForm = () => {
  Object.assign(formData, {
    treatmentDate: new Date(),
    signsSymptoms: "",
    tongue: "",
    pulse: "",
    investigations: "",
    results: "",
    totalAmount: 0,
    amountPaid: 0,
    balance: 0,
    paidStatus: null,
  });
  tableData.value = [];
  selectedRow.value = null;
};
</script>

<style scoped>
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
  border-radius: 8px !important;
}

.btn-accent:hover {
  background: var(--emerald-700) !important;
  border-color: var(--emerald-700) !important;
}

.section-actions {
  display: flex;
  gap: 0.5rem;
}

.drug-name {
  font-weight: 600;
  color: var(--slate-900);
}

.cell-mono {
  font-family: monospace;
  font-size: 0.85rem;
  color: var(--slate-600);
}
</style>
