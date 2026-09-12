<template>
  <div class="treatment-by-date-page">
    <div class="page-card">
      <div class="page-card-header">
        <div>
          <h3>Treatments By Date</h3>
          <p>Search treatments and panchkarma by date range</p>
        </div>
      </div>

      <!-- Date Range & Actions -->
      <div class="date-bar">
        <div class="date-fields">
          <div class="date-field">
            <label>Start Date</label>
            <Calendar v-model="startDate" dateFormat="dd/mm/yy" showIcon />
          </div>
          <div class="date-field">
            <label>End Date</label>
            <Calendar v-model="endDate" dateFormat="dd/mm/yy" showIcon />
          </div>
        </div>
        <div class="action-buttons">
          <Button
            label="Submit"
            icon="pi pi-search"
            class="btn-accent"
            @click="fetchData"
            :loading="loading"
          />
          <Button
            label="Calculate"
            icon="pi pi-calculator"
            severity="info"
            @click="calculate"
            :disabled="treatments.length === 0 && panchkarmaList.length === 0"
          />
        </div>
      </div>

      <!-- Loading -->
      <div v-if="loading" class="loading-container">
        <ProgressSpinner style="width: 50px; height: 50px" />
      </div>

      <!-- Error -->
      <Message v-if="errorMessage" severity="error" :closable="false" class="msg">
        {{ errorMessage }}
      </Message>

      <!-- Calculation Summary -->
      <div v-if="showSummary" class="summary-section">
        <Panel header="Calculation Summary">
          <DataTable :value="summaryRows" class="summary-table">
            <Column field="subject" header="Subject" style="width: 40%" />
            <Column field="count" header="Number of" style="width: 30%" />
            <Column field="totalAmount" header="Total Amount" style="width: 30%">
              <template #body="slotProps">
                {{ formatCurrency(slotProps.data.totalAmount) }}
              </template>
            </Column>
          </DataTable>
          <div class="summary-totals">
            <div class="summary-row">
              <span>Treatment Total No: <strong>{{ treatmentCount }}</strong></span>
              <span>Treatment TotalCost: <strong>{{ formatCurrency(treatmentTotal) }}</strong></span>
              <span>New Treatments: <strong>{{ newTreatmentCount }}</strong></span>
            </div>
            <div class="summary-row">
              <span>Panchkarma Total No: <strong>{{ panchkarmaCount }}</strong></span>
              <span>Panchkarma TotalCost: <strong>{{ formatCurrency(panchkarmaTotal) }}</strong></span>
            </div>
            <div class="summary-grand-total">
              Grand Total: <strong>{{ formatCurrency(grandTotal) }}</strong>
            </div>
          </div>
        </Panel>
      </div>

      <!-- Treatment List -->
      <div v-if="treatments.length > 0" class="table-section">
        <Panel header="Treatment List" toggleable>
          <DataTable
            :value="treatments"
            scrollHeight="40vh"
            :rowHover="true"
            class="data-table"
          >
            <Column header="Treatment Date" style="width: 12%">
              <template #body="slotProps">
                {{ formatDate(slotProps.data.treatmentDate) }}
              </template>
            </Column>
            <Column header="First Name" style="width: 14%">
              <template #body="slotProps">
                {{ slotProps.data.patient?.firstName || '' }}
              </template>
            </Column>
            <Column header="Last Name" style="width: 14%">
              <template #body="slotProps">
                {{ slotProps.data.patient?.lastName || '' }}
              </template>
            </Column>
            <Column header="Mobile No" style="width: 14%">
              <template #body="slotProps">
                {{ slotProps.data.patient?.mobile1 || slotProps.data.patient?.MOBILE1 || '' }}
              </template>
            </Column>
            <Column header="Landline" style="width: 12%">
              <template #body="slotProps">
                {{ slotProps.data.patient?.landline || '' }}
              </template>
            </Column>
            <Column field="paid" header="Paid" style="width: 8%">
              <template #body="slotProps">
                <span :class="['paid-badge', slotProps.data.paid === 'yes' ? 'paid-yes' : 'paid-no']">
                  {{ slotProps.data.paid }}
                </span>
              </template>
            </Column>
            <Column field="paymentType" header="Payment Type" style="width: 12%">
              <template #body="slotProps">
                <span class="cell-text">{{ slotProps.data.paymentType || '' }}</span>
              </template>
            </Column>
            <Column header="Amount" style="width: 10%">
              <template #body="slotProps">
                {{ slotProps.data.amountPaid != null ? formatCurrency(slotProps.data.amountPaid) : '' }}
              </template>
            </Column>
          </DataTable>
        </Panel>
      </div>

      <!-- Panchkarma Treatment List -->
      <div v-if="panchkarmaList.length > 0" class="table-section">
        <Panel header="Panchkarma Treatment List" toggleable>
          <DataTable
            :value="panchkarmaList"
            scrollHeight="40vh"
            :rowHover="true"
            class="data-table"
          >
            <Column header="Start Date" style="width: 11%">
              <template #body="slotProps">
                {{ formatDate(slotProps.data.startDate) }}
              </template>
            </Column>
            <Column header="End Date" style="width: 11%">
              <template #body="slotProps">
                {{ formatDate(slotProps.data.endDate) }}
              </template>
            </Column>
            <Column header="Panchkarma Name" style="width: 15%">
              <template #body="slotProps">
                {{ slotProps.data.panchkarma?.panchkarmaName || '' }}
              </template>
            </Column>
            <Column header="First Name" style="width: 12%">
              <template #body="slotProps">
                {{ slotProps.data.patient?.firstName || '' }}
              </template>
            </Column>
            <Column header="Last Name" style="width: 12%">
              <template #body="slotProps">
                {{ slotProps.data.patient?.lastName || '' }}
              </template>
            </Column>
            <Column header="Mobile No" style="width: 12%">
              <template #body="slotProps">
                {{ slotProps.data.patient?.mobile1 || slotProps.data.patient?.MOBILE1 || '' }}
              </template>
            </Column>
            <Column header="Landline" style="width: 10%">
              <template #body="slotProps">
                {{ slotProps.data.patient?.landline || '' }}
              </template>
            </Column>
            <Column header="Paid" style="width: 7%">
              <template #body="slotProps">
                <span :class="['paid-badge', slotProps.data.paid === 'yes' ? 'paid-yes' : 'paid-no']">
                  {{ slotProps.data.paid }}
                </span>
              </template>
            </Column>
            <Column header="Amount Paid" style="width: 10%">
              <template #body="slotProps">
                {{ slotProps.data.amountPaid != null ? formatCurrency(slotProps.data.amountPaid) : '' }}
              </template>
            </Column>
          </DataTable>
        </Panel>
      </div>

      <!-- No Results -->
      <Message
        v-if="!loading && searched && treatments.length === 0 && panchkarmaList.length === 0"
        severity="info"
        :closable="false"
        class="msg"
      >
        No treatments or panchkarma found for the selected date range.
      </Message>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import DataTable from "primevue/datatable";
import Column from "primevue/column";
import Button from "primevue/button";
import Calendar from "primevue/calendar";
import Panel from "primevue/panel";
import Message from "primevue/message";
import ProgressSpinner from "primevue/progressspinner";

const now = new Date();
const startDate = ref(new Date(now.getFullYear(), now.getMonth(), 1));
const endDate = ref(new Date(now.getFullYear(), now.getMonth() + 1, 0));

const treatments = ref([]);
const panchkarmaList = ref([]);
const loading = ref(false);
const errorMessage = ref("");
const searched = ref(false);
const showSummary = ref(false);

const summaryRows = ref([]);
const treatmentCount = ref(0);
const treatmentTotal = ref(0);
const panchkarmaCount = ref(0);
const panchkarmaTotal = ref(0);
const grandTotal = ref(0);
const newTreatmentCount = ref(0);

const formatDate = (dateStr) => {
  if (!dateStr) return "";
  const d = new Date(dateStr);
  return d.toLocaleDateString("en-IN");
};

const formatCurrency = (value) => {
  if (value == null) return "";
  return Number(value).toLocaleString("en-IN");
};

const toApiDate = (date) => {
  const d = new Date(date);
  const year = d.getFullYear();
  const month = String(d.getMonth() + 1).padStart(2, "0");
  const day = String(d.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
};

const fetchData = async () => {
  if (!startDate.value || !endDate.value) {
    errorMessage.value = "Please select both start and end dates";
    return;
  }

  loading.value = true;
  errorMessage.value = "";
  searched.value = true;
  showSummary.value = false;

  const start = toApiDate(startDate.value);
  const end = toApiDate(endDate.value);

  try {
    const [treatmentResp, panchkarmaResp] = await Promise.all([
      axios.get("http://localhost:8080/treatmentsByDate", {
        params: { startDate: start, endDate: end },
      }),
      axios.get("http://localhost:8080/panchkarmaByDate", {
        params: { startDate: start, endDate: end },
      }),
    ]);

    treatments.value = treatmentResp.data.content || [];
    panchkarmaList.value = panchkarmaResp.data.content || [];
  } catch (error) {
    errorMessage.value = `Error fetching data: ${error.message}`;
    treatments.value = [];
    panchkarmaList.value = [];
  } finally {
    loading.value = false;
  }
};

const calculate = () => {
  treatmentCount.value = treatments.value.length;
  treatmentTotal.value = treatments.value.reduce(
    (sum, t) => sum + (t.amountPaid || 0),
    0
  );

  panchkarmaCount.value = panchkarmaList.value.length;
  panchkarmaTotal.value = panchkarmaList.value.reduce(
    (sum, p) => sum + (p.amountPaid || 0),
    0
  );

  // Count "new" treatments (first treatment for a patient in this range)
  const patientFirstTreatment = new Set();
  treatments.value.forEach((t) => {
    patientFirstTreatment.add(t.patientId);
  });
  newTreatmentCount.value = patientFirstTreatment.size;

  grandTotal.value = treatmentTotal.value + panchkarmaTotal.value;

  summaryRows.value = [
    {
      subject: "Treatments",
      count: treatmentCount.value,
      totalAmount: treatmentTotal.value,
    },
    {
      subject: "Panchkarma Treatments",
      count: panchkarmaCount.value,
      totalAmount: panchkarmaTotal.value,
    },
  ];

  showSummary.value = true;
};
</script>

<style scoped>
.treatment-by-date-page {
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

.date-bar {
  padding: 1rem 1.5rem;
  display: flex;
  gap: 1.5rem;
  align-items: flex-end;
  flex-wrap: wrap;
  border-bottom: 1px solid var(--slate-100);
}

.date-fields {
  display: flex;
  gap: 1rem;
}

.date-field {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}

.date-field label {
  font-size: 0.75rem;
  font-weight: 600;
  color: var(--slate-600);
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

.action-buttons {
  display: flex;
  gap: 0.5rem;
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

.loading-container {
  display: flex;
  justify-content: center;
  padding: 2rem;
}

.msg {
  margin: 0.75rem 1.5rem;
}

.table-section {
  padding: 1rem 1.5rem;
}

.summary-section {
  padding: 1rem 1.5rem;
}

.summary-totals {
  margin-top: 1rem;
  padding: 1rem;
  background: var(--slate-50);
  border-radius: 8px;
}

.summary-row {
  display: flex;
  gap: 2rem;
  padding: 0.25rem 0;
  font-size: 0.85rem;
  color: var(--slate-700);
}

.summary-grand-total {
  margin-top: 0.5rem;
  padding-top: 0.5rem;
  border-top: 2px solid var(--slate-300);
  font-size: 1rem;
  font-weight: 700;
  color: var(--slate-900);
}

.cell-text {
  color: var(--slate-700);
  font-weight: 500;
}

.paid-badge {
  display: inline-block;
  padding: 0.2rem 0.5rem;
  border-radius: 999px;
  font-size: 0.7rem;
  font-weight: 600;
}

.paid-yes {
  background: #d1fae5;
  color: #065f46;
}

.paid-no {
  background: #fee2e2;
  color: #991b1b;
}

.data-table {
  font-size: 0.85rem;
}

.summary-table {
  font-size: 0.85rem;
}
</style>
