<template>
  <div>
    <Dialog
      :header="panchkarmaData ? 'Edit Panchkarma Treatment' : 'Add Panchkarma Treatment'"
      v-model:visible="visibleModel"
      :style="{ width: '60vw' }"
      :modal="true"
    >
      <div class="p-fluid">
        <!-- Panchkarma Name -->
        <div class="row mb-3">
          <div class="col-6">
            <label for="panchkarmaName" class="field-label">Panchkarma Name</label>
            <InputText
              id="panchkarmaName"
              v-model="formData.panchkarmaName"
              class="w-full"
              placeholder="Enter panchkarma name"
            />
          </div>
          <div class="col-3">
            <label for="cost" class="field-label">Cost</label>
            <InputNumber id="cost" v-model="formData.cost" class="w-full" />
          </div>
          <div class="col-3">
            <label for="noOfDays" class="field-label">No of Days</label>
            <InputNumber id="noOfDays" v-model="formData.noOfDays" class="w-full" :min="1" />
          </div>
        </div>

        <!-- Dates -->
        <div class="row mb-3">
          <div class="col-6">
            <label for="startDate" class="field-label">Start Date</label>
            <DatePicker id="startDate" v-model="formData.startDate" class="w-full" />
          </div>
          <div class="col-6">
            <label for="endDate" class="field-label">End Date</label>
            <DatePicker id="endDate" v-model="formData.endDate" class="w-full" />
          </div>
        </div>

        <!-- Procedure -->
        <div class="row mb-3">
          <div class="col-12">
            <label for="procd" class="field-label">Procedure</label>
            <Textarea id="procd" v-model="formData.procd" rows="2" class="w-full" fluid />
          </div>
        </div>

        <!-- Diseases -->
        <div class="row mb-3">
          <div class="col-12">
            <label for="diseases" class="field-label">Diseases</label>
            <Textarea id="diseases" v-model="formData.diseases" rows="2" class="w-full" fluid />
          </div>
        </div>

        <!-- Results -->
        <div class="row mb-3">
          <div class="col-12">
            <label for="results" class="field-label">Results / Remarks</label>
            <Textarea id="results" v-model="formData.results" rows="2" class="w-full" fluid />
          </div>
        </div>

        <!-- Amount Section -->
        <div class="row mb-3">
          <div class="col-4">
            <label for="amountPaid" class="field-label">Amount Paid</label>
            <InputNumber id="amountPaid" v-model="formData.amountPaid" class="w-full" @input="calculateBalance" />
          </div>
          <div class="col-4">
            <label for="balance" class="field-label">Balance</label>
            <InputNumber id="balance" v-model="formData.balance" class="w-full" :disabled="true" />
          </div>
          <div class="col-4">
            <label for="paidStatus" class="field-label">Paid Status</label>
            <Select
              id="paidStatus"
              v-model="formData.paid"
              :options="paidStatusOptions"
              placeholder="Select Status"
              class="w-full"
            />
          </div>
        </div>
      </div>

      <template #footer>
        <Button label="Cancel" icon="pi pi-times" text @click="visibleModel = false" />
        <Button label="Save" icon="pi pi-check" class="btn-accent" @click="submitData" />
      </template>
    </Dialog>
  </div>
</template>

<script setup>
import { reactive, watch } from "vue";
import {
  Button,
  DatePicker,
  Dialog,
  InputNumber,
  InputText,
  Select,
  Textarea,
} from "primevue";
import apiService from "@/api/apiservice";

const props = defineProps({
  panchkarmaData: {
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

const paidStatusOptions = ["Y", "N"];

const formData = reactive({
  patientPanchId: null,
  panchkarmaName: "",
  cost: 0,
  noOfDays: 1,
  startDate: new Date(),
  endDate: null,
  procd: "",
  diseases: "",
  results: "",
  amountPaid: 0,
  balance: 0,
  paid: null,
  paymentType: null,
  panchkarmaId: null,
});

watch(
  () => props.panchkarmaData,
  (newData) => {
    if (newData) {
      formData.patientPanchId = newData.patientPanchId || null;
      formData.panchkarmaName = newData.panchkarma?.panchkarmaName || "";
      formData.cost = newData.panchkarma?.cost || 0;
      formData.noOfDays = newData.panchkarma?.noOfDays || 1;
      formData.startDate = newData.startDate ? new Date(newData.startDate) : new Date();
      formData.endDate = newData.endDate ? new Date(newData.endDate) : null;
      formData.procd = newData.panchkarma?.procd || "";
      formData.diseases = newData.panchkarma?.diseases || "";
      formData.results = newData.results || "";
      formData.amountPaid = newData.amountPaid || 0;
      formData.balance = newData.balance || 0;
      formData.paid = newData.paid || null;
      formData.paymentType = newData.paymentType || null;
      formData.panchkarmaId = newData.panchkarma?.panchkarmaId || null;
    } else {
      resetForm();
    }
  }
);

const calculateBalance = () => {
  formData.balance = formData.cost - formData.amountPaid;
};

const submitData = async () => {
  if (!formData.panchkarmaName) {
    alert("Please enter a panchkarma name");
    return;
  }

  const payload = {
    patientPanchId: formData.patientPanchId,
    patientId: props.patientId,
    panchkarma: {
      panchkarmaId: formData.panchkarmaId,
      panchkarmaName: formData.panchkarmaName,
      cost: formData.cost,
      procd: formData.procd,
      diseases: formData.diseases,
      noOfDays: formData.noOfDays,
    },
    startDate: formData.startDate,
    endDate: formData.endDate,
    results: formData.results,
    amountPaid: formData.amountPaid,
    paid: formData.paid,
    paymentType: formData.paymentType,
    balance: formData.balance,
  };

  try {
    await apiService.patientPanchkarma.savePatientPanchkarma(payload);
    visibleModel.value = false;
    emit("saved");
    resetForm();
  } catch (error) {
    console.error("Error saving panchkarma:", error.message);
    alert("Error saving panchkarma treatment");
  }
};

const resetForm = () => {
  Object.assign(formData, {
    patientPanchId: null,
    panchkarmaName: "",
    cost: 0,
    noOfDays: 1,
    startDate: new Date(),
    endDate: null,
    procd: "",
    diseases: "",
    results: "",
    amountPaid: 0,
    balance: 0,
    paid: null,
    paymentType: null,
    panchkarmaId: null,
  });
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
</style>
