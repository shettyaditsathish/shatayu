<template>
  <div>
    <Dialog
      header="Add Treatment"
      v-model:visible="visibleModel"
      :style="{ width: '70vw' }"
      :modal="true"
    >
      <div class="p-fluid">
        <!-- Treatment Date -->
        <div class="p-field p-grid">
          <label for="treatmentDate" class="p-col-12 p-mb-2 p-md-3 p-mb-md-0"
            >Treatment Date</label
          >
          <div class="p-col-12 p-md-9">
            <DatePicker v-model="formData.treatmentDate" />
          </div>
        </div>
        <div class="row mt-3">
          <div class="p-col-12 p-md-6">
            <label for="tongue" class="p-mb-2">Signs and Symptoms</label><br />
            <Textarea id="tongue" v-model="formData.signsAndSymptoms" />
          </div>
        </div>

        <!-- Signs and Symptoms -->
        <div class="p-field row mt-3">
          <div class="col-6 col-md-6">
            <label for="tongue" class="p-mb-2 w-100">Tongue</label><br />
            <Textarea id="tongue" v-model="formData.tongue" />
          </div>
          <div class="p-col-6 col-md-6">
            <label for="pulse" class="p-mb-2">Pulse</label><br />
            <Textarea id="pulse" v-model="formData.pulse" />
          </div>
        </div>

        <!-- Investigations -->
        <div class="p-field p-grid p-mt-3">
          <label for="investigations" class="p-col-12 p-mb-2"
            >Investigations</label
          >
          <div class="p-col-12">
            <Textarea
              id="investigations"
              v-model="formData.investigations"
              rows="3"
            />
          </div>
        </div>

        <!-- Amount Section -->
        <div class="row mt-3">
          <div class="col-4">
            <label for="totalAmount">Total Amount</label>
            <div class="p-inputgroup">
              <InputNumber id="totalAmount" v-model="formData.totalAmount" />
              <Button label="calc" @click="calculateBalance" />
            </div>
          </div>
          <div class="col-4">
            <label for="amountPaid" class="p-mb-2">Amount Paid</label><br />
            <InputNumber
              id="amountPaid"
              v-model="formData.amountPaid"
              @input="calculateBalance"
            />
          </div>
          <div class="col-4">
            <label for="balance" class="p-mb-2">Balance</label><br />
            <InputNumber
              id="balance"
              v-model="formData.balance"
              :disabled="true"
            />
          </div>
        </div>

        <!-- Paid Status -->
        <div class="d-flex mt-3">
          <label for="paidStatus" class="p-col-12 mt-2 me-2">Paid Status</label>
          <div class="p-col-12 p-md-9">
            <Select
              id="paidStatus"
              v-model="formData.paidStatus"
              :options="paidStatusOptions"
              placeholder="Select Status"
            />
          </div>
        </div>

        <!-- Action Buttons -->
        <div class="mt-5">
          <Button
            label="Add Row"
            icon="pi pi-plus"
            class="p-button-success p-mr-2"
            @click="addRow"
          />
          <Button
            label="Remove Row"
            icon="pi pi-minus"
            class="p-button-danger"
            @click="removeRow"
          />
        </div>

        <!-- Data Table -->
        <div class="p-mt-3">
          <DataTable
            :value="tableData"
            v-model:selection="selectedRow"
            selectionMode="single"
            dataKey="id"
            class="p-datatable-sm"
          >
            <Column field="drugId" header="drugId">
              <template #body="slotProps">
                <Select
                  v-if="slotProps.data.isEditing"
                  v-model="slotProps.data.drugId"
                  :options="drugOptions"
                  optionLabel="name"
                  optionValue="id"
                  placeholder="Select Drug"
                  class="p-inputtext-sm"
                />
                <span v-else>{{ getDrugName(slotProps.data.drugId) }}</span>
              </template>
            </Column>

            <Column field="duration" header="duration">
              <template #body="slotProps">
                <div v-if="slotProps.data.isEditing" class="d-flex">
                  <div>
                    <InputNumber
                      v-model="slotProps.data.duration.morning"
                      :min="0"
                      :max="9"
                      :pt="{
                        pcinputtext: { style: 'width: 60px' },
                      }"
                      :input-style="{ width: '60px' }"
                    />
                  </div>

                  <span class="mt-2 ms-2 me-2"> - </span>
                  <div>
                    <InputNumber
                      v-model="slotProps.data.duration.afternoon"
                      :min="0"
                      :max="9"
                      :input-style="{ width: '60px' }"
                    />
                  </div>

                  <span class="mt-2 ms-2 me-2">-</span>
                  <div>
                    <InputNumber
                      v-model="slotProps.data.duration.night"
                      :min="0"
                      :max="9"
                      :input-style="{ width: '60px' }"
                    />
                  </div>
                </div>
                <span v-else>{{
                  formatDuration(slotProps.data.duration)
                }}</span>
              </template>
            </Column>

            <Column field="noofdays" header="noofdays">
              <template #body="slotProps">
                <InputNumber
                  v-if="slotProps.data.isEditing"
                  v-model="slotProps.data.noofdays"
                  :min="1"
                  class="p-inputtext-sm"
                />
                <span v-else>{{ slotProps.data.noofdays }}</span>
              </template>
            </Column>

            <Column field="emptyCol" header="emptyCol">
              <template #body>
                <!-- Empty column -->
              </template>
            </Column>
          </DataTable>
        </div>
      </div>

      <template #footer>
        <Button
          label="Cancel"
          icon="pi pi-times"
          class="p-button-text"
          @click="showDialog = false"
        />
        <Button
          label="Submit Data"
          icon="pi pi-check"
          class="p-button-success"
          @click="submitData"
        />
      </template>
    </Dialog>
  </div>
</template>

<script setup>
import {
  Button,
  Column,
  DataTable,
  DatePicker,
  Dialog,
  InputNumber,
  InputText,
  Select,
  Textarea,
} from "primevue";
import { ref, reactive } from "vue";

const showDialog = ref(false);
const selectedRow = ref(null);
const nextId = ref(1);

let visibleModel = defineModel("showModal", {
  type: Boolean,
  required: true,
});

const formData = reactive({
  treatmentDate: new Date(),
  tongue: "",
  pulse: "",
  investigations: "",
  totalAmount: 0,
  amountPaid: 0,
  balance: 0,
  paidStatus: null,
});

const paidStatusOptions = ["Y", "N"];

const tableData = ref([]);

const drugOptions = [
  { id: 1, name: "Paracetamol" },
  { id: 2, name: "Ibuprofen" },
  { id: 3, name: "Amoxicillin" },
  { id: 4, name: "Aspirin" },
  { id: 5, name: "Metformin" },
];

const addRow = () => {
  tableData.value.push({
    id: nextId.value++,
    drugId: null,
    duration: {
      morning: 1,
      afternoon: 1,
      night: 1,
    },
    noofdays: 1,
    isEditing: true,
  });
};

const removeRow = () => {
  if (selectedRow.value) {
    const index = tableData.value.findIndex(
      (row) => row.id === selectedRow.value.id
    );
    if (index > -1) {
      tableData.value.splice(index, 1);
      selectedRow.value = null;
    }
  } else {
    toast.add({
      severity: "warn",
      summary: "Warning",
      detail: "Please select a row to remove",
      life: 3000,
    });
  }
};

const calculateBalance = () => {
  formData.balance = formData.totalAmount - formData.amountPaid;
};

const getDrugName = (drugId) => {
  const drug = drugOptions.find((d) => d.id === drugId);
  return drug ? drug.name : "";
};

const formatDuration = (duration) => {
  return `${duration.morning}-${duration.afternoon}-${duration.night}`;
};

const submitData = () => {
  // Validate data
  if (!formData.treatmentDate) {
    alert("Please select a treatment date");
    return;
  }

  // Set all rows to non-editing mode
  tableData.value.forEach((row) => {
    row.isEditing = false;
  });

  const submitPayload = {
    ...formData,
    prescriptions: tableData.value.map((row) => ({
      drugId: row.drugId,
      duration: formatDuration(row.duration),
      noofdays: row.noofdays,
    })),
  };

  console.log("Submitting data:", submitPayload);

  // Here you would typically make an API call
  // fetch('/api/treatments', { method: 'POST', body: JSON.stringify(submitPayload) })

  alert("Data submitted successfully!");
  showDialog.value = false;
  resetForm();
};

const resetForm = () => {
  Object.assign(formData, {
    treatmentDate: new Date(),
    tongue: "",
    pulse: "",
    investigations: "",
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
.p-field {
  margin-bottom: 1rem;
}

.duration-input {
  width: 60px;
}

.p-inputgroup-addon {
  padding: 0.5rem;
}

.p-datatable-sm .p-datatable-tbody > tr > td {
  padding: 0.5rem;
}
</style>
