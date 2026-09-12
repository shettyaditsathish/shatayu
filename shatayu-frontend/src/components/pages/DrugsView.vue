<template>
  <div class="drugs-page">
    <!-- Page Header Card -->
    <div class="page-card">
      <div class="page-card-header">
        <div>
          <h3>Aushadhi Inventory</h3>
          <p>Manage classic herbal formulations and capsules</p>
        </div>
        <div class="header-actions">
          <Button
            label="Add New Formulation"
            icon="pi pi-plus"
            class="btn-accent"
            @click="openNewDrug"
          />
        </div>
      </div>

      <!-- Search Section -->
      <div class="search-bar">
        <div class="search-input-wrapper">
          <i class="pi pi-search"></i>
          <input
            v-model="searchQuery"
            type="text"
            placeholder="Search by drug name..."
            @keydown.enter="searchDrugs"
          />
        </div>
        <Button
          label="Search"
          icon="pi pi-search"
          class="btn-accent"
          @click="searchDrugs"
          :loading="loading"
        />
        <Button
          label="Clear"
          icon="pi pi-times"
          severity="secondary"
          outlined
          @click="clearSearch"
          :disabled="!searchQuery && drugs.length === 0"
        />
        <Button
          v-if="selectedDrug"
          label="Delete Drug"
          icon="pi pi-trash"
          severity="danger"
          outlined
          @click="confirmDeleteDrug"
        />
      </div>

      <!-- Error Message -->
      <div v-if="errorMessage" class="msg-area">
        <Message severity="error" :closable="false">
          {{ errorMessage }}
        </Message>
      </div>

      <!-- No Results Message -->
      <div v-if="!loading && searched && drugs.length === 0" class="msg-area">
        <Message severity="info" :closable="false">
          No drugs found for "{{ lastSearchQuery }}"
        </Message>
      </div>

      <!-- Loading -->
      <div v-if="loading" class="loading-container">
        <ProgressSpinner style="width: 50px; height: 50px" />
      </div>

      <!-- DataTable -->
      <DataTable
        v-else
        :value="drugs"
        v-model:selection="selectedDrug"
        selectionMode="single"
        dataKey="drugId"
        :rowHover="true"
        scrollHeight="60vh"
        class="drugs-table"
        @row-click="onRowClick"
      >
        <Column field="drugName" header="Drug Name" sortable style="width: 25%">
          <template #body="slotProps">
            <span class="drug-name">{{ slotProps.data.drugName }}</span>
          </template>
        </Column>
        <Column field="drugCost" header="Cost" sortable style="width: 12%">
          <template #body="slotProps">
            <span class="cell-mono">{{ slotProps.data.drugCost }}</span>
          </template>
        </Column>
        <Column field="contents" header="Contents" style="width: 23%" />
        <Column field="quantity" header="Qty" sortable style="width: 10%">
          <template #body="slotProps">
            <span class="qty-badge" :class="{ 'qty-low': slotProps.data.quantity !== null && slotProps.data.quantity <= 10 }">
              {{ slotProps.data.quantity ?? '-' }}
            </span>
          </template>
        </Column>
        <Column field="details" header="Details" style="width: 20%" />
        <Column field="inactive" header="Status" sortable style="width: 10%">
          <template #body="slotProps">
            <span :class="['status-badge', slotProps.data.inactive === 'T' ? 'badge-inactive' : 'badge-active']">
              {{ slotProps.data.inactive === 'T' ? 'Inactive' : 'Active' }}
            </span>
          </template>
        </Column>
      </DataTable>
    </div>

    <AddDrugModal
      v-model:showModal="showDrugModal"
      :drug="drugForEdit"
      @saved="onDrugSaved"
    />

    <Dialog
      v-model:visible="showDeleteConfirm"
      header="Confirm Delete"
      :style="{ width: '25rem' }"
      :modal="true"
    >
      <div class="d-flex align-items-center gap-2 mb-3">
        <i class="pi pi-exclamation-triangle" style="font-size: 1.5rem; color: #d97706" />
        <span>Are you sure you want to delete <strong>{{ selectedDrug?.drugName }}</strong>?</span>
      </div>
      <template #footer>
        <Button label="No" icon="pi pi-times" text @click="showDeleteConfirm = false" />
        <Button label="Yes" icon="pi pi-check" severity="danger" @click="deleteDrug" />
      </template>
    </Dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import Button from "primevue/button";
import DataTable from "primevue/datatable";
import Column from "primevue/column";
import Dialog from "primevue/dialog";
import Message from "primevue/message";
import ProgressSpinner from "primevue/progressspinner";
import apiService from "@/api/apiservice";
import AddDrugModal from "@/components/pages/saveviews/AddDrugModal.vue";

const searchQuery = ref("");
const drugs = ref([]);
const loading = ref(false);
const searched = ref(false);
const lastSearchQuery = ref("");
const selectedDrug = ref(null);
const showDrugModal = ref(false);
const drugForEdit = ref(null);
const showDeleteConfirm = ref(false);

const loadAllDrugs = async () => {
  loading.value = true;
  try {
    const resp = await apiService.instance.get("/drugs");
    drugs.value = resp.data || [];
  } catch (error) {
    console.error("Error loading drugs:", error.message);
  } finally {
    loading.value = false;
  }
};

const searchDrugs = async () => {
  if (!searchQuery.value.trim()) {
    errorMessage.value = "Please enter a search query";
    return;
  }

  loading.value = true;
  errorMessage.value = "";
  searched.value = true;
  lastSearchQuery.value = searchQuery.value;

  try {
    const resp = await apiService.instance.get("/drugs/search", {
      params: { name: searchQuery.value },
    });
    drugs.value = resp.data || [];
  } catch (error) {
    errorMessage.value = `Error searching drugs: ${error.message}`;
    drugs.value = [];
  } finally {
    loading.value = false;
  }
};

const clearSearch = () => {
  searchQuery.value = "";
  errorMessage.value = "";
  searched.value = false;
  lastSearchQuery.value = "";
  selectedDrug.value = null;
  loadAllDrugs();
};

const errorMessage = ref("");

const openNewDrug = () => {
  drugForEdit.value = null;
  showDrugModal.value = true;
};

const onRowClick = (event) => {
  drugForEdit.value = event.data;
  showDrugModal.value = true;
};

const confirmDeleteDrug = () => {
  if (!selectedDrug.value) return;
  showDeleteConfirm.value = true;
};

const deleteDrug = async () => {
  if (!selectedDrug.value) return;

  try {
    await apiService.instance.delete(`/drug/${selectedDrug.value.drugId}`);
    selectedDrug.value = null;
    showDeleteConfirm.value = false;
    if (searched.value && lastSearchQuery.value) {
      searchDrugs();
    } else {
      loadAllDrugs();
    }
  } catch (error) {
    console.error("Error deleting drug:", error.message);
    alert("Error deleting drug");
  }
};

const onDrugSaved = () => {
  if (searched.value && lastSearchQuery.value) {
    searchDrugs();
  } else {
    loadAllDrugs();
  }
};

onMounted(() => {
  loadAllDrugs();
});
</script>

<style scoped>
.drugs-page {
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

.header-actions {
  display: flex;
  align-items: center;
  gap: 0.75rem;
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

/* Search */
.search-bar {
  padding: 1rem 1.5rem;
  display: flex;
  gap: 0.5rem;
  align-items: center;
}

.search-input-wrapper {
  flex: 1;
  position: relative;
}

.search-input-wrapper i {
  position: absolute;
  left: 0.75rem;
  top: 50%;
  transform: translateY(-50%);
  color: var(--slate-400);
  font-size: 0.875rem;
}

.search-input-wrapper input {
  width: 100%;
  padding: 0.5rem 1rem 0.5rem 2.25rem;
  background: var(--slate-50);
  border: 1px solid var(--slate-200);
  border-radius: 8px;
  font-size: 0.85rem;
  color: var(--slate-700);
  outline: none;
  transition: all 0.2s;
}

.search-input-wrapper input:focus {
  border-color: var(--emerald-500);
  background: white;
  box-shadow: 0 0 0 3px rgba(5, 150, 105, 0.1);
}

.search-input-wrapper input::placeholder {
  color: var(--slate-400);
}

.msg-area {
  padding: 0 1.5rem;
}

.loading-container {
  display: flex;
  justify-content: center;
  padding: 2rem;
}

/* Table cell styles */
.drug-name {
  font-weight: 600;
  color: var(--slate-900);
}

.cell-mono {
  font-family: monospace;
  font-size: 0.8rem;
  color: var(--slate-600);
}

.qty-badge {
  display: inline-block;
  padding: 0.2rem 0.5rem;
  border-radius: 6px;
  font-size: 0.75rem;
  font-weight: 600;
  background: var(--slate-100);
  color: var(--slate-600);
}

.qty-low {
  background: #fef2f2;
  color: #dc2626;
}

.status-badge {
  display: inline-flex;
  align-items: center;
  gap: 0.375rem;
  padding: 0.2rem 0.625rem;
  border-radius: 999px;
  font-size: 0.7rem;
  font-weight: 600;
}

.badge-active {
  background: var(--emerald-50);
  color: var(--emerald-700);
}

.badge-inactive {
  background: var(--slate-100);
  color: var(--slate-500);
}

.drugs-table {
  cursor: pointer;
}
</style>
