<template>
  <div>
    <Dialog
      :header="drug ? 'Edit Drug' : 'Add Drug'"
      v-model:visible="visibleModel"
      :style="{ width: '50vw' }"
      :modal="true"
    >
      <div class="p-fluid">
        <div class="row mb-3">
          <div class="col-6">
            <label for="drugName" class="field-label">Drug Name</label>
            <InputText id="drugName" v-model="formData.drugName" class="w-full" placeholder="Enter drug name" />
          </div>
          <div class="col-3">
            <label for="drugCost" class="field-label">Cost</label>
            <InputNumber id="drugCost" v-model="formData.drugCost" class="w-full" :minFractionDigits="2" />
          </div>
          <div class="col-3">
            <label for="quantity" class="field-label">Quantity</label>
            <InputNumber id="quantity" v-model="formData.quantity" class="w-full" />
          </div>
        </div>

        <div class="row mb-3">
          <div class="col-12">
            <label for="contents" class="field-label">Contents</label>
            <Textarea id="contents" v-model="formData.contents" rows="2" class="w-full" fluid />
          </div>
        </div>

        <div class="row mb-3">
          <div class="col-12">
            <label for="details" class="field-label">Details</label>
            <Textarea id="details" v-model="formData.details" rows="2" class="w-full" fluid />
          </div>
        </div>

        <div class="row mb-3">
          <div class="col-3">
            <label for="inactive" class="field-label">Status</label>
            <Select
              id="inactive"
              v-model="formData.inactive"
              :options="inactiveOptions"
              placeholder="Select"
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
  Dialog,
  InputNumber,
  InputText,
  Select,
  Textarea,
} from "primevue";
import apiService from "@/api/apiservice";

const props = defineProps({
  drug: {
    type: Object,
    default: null,
  },
});

const emit = defineEmits(["saved"]);

let visibleModel = defineModel("showModal", {
  type: Boolean,
  required: true,
});

const inactiveOptions = ["T", "F"];

const formData = reactive({
  drugId: null,
  drugName: "",
  drugCost: 0,
  contents: "",
  quantity: null,
  details: "",
  inactive: "F",
});

const resetForm = () => {
  Object.assign(formData, {
    drugId: null,
    drugName: "",
    drugCost: 0,
    contents: "",
    quantity: null,
    details: "",
    inactive: "F",
  });
};

watch(
  () => props.drug,
  (newDrug) => {
    if (newDrug) {
      formData.drugId = newDrug.drugId ?? null;
      formData.drugName = newDrug.drugName ?? "";
      formData.drugCost = newDrug.drugCost ?? 0;
      formData.contents = newDrug.contents ?? "";
      formData.quantity = newDrug.quantity ?? null;
      formData.details = newDrug.details ?? "";
      formData.inactive = newDrug.inactive ?? "F";
    } else {
      resetForm();
    }
  },
  { immediate: true }
);

const submitData = async () => {
  if (!formData.drugName) {
    alert("Please enter a drug name");
    return;
  }

  try {
    await apiService.instance.post("/drug", { ...formData });
    visibleModel.value = false;
    emit("saved");
    resetForm();
  } catch (error) {
    console.error("Error saving drug:", error.message);
    alert("Error saving drug");
  }
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
