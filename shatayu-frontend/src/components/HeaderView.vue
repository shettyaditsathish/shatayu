<template>
  <header class="app-header">
    <div class="header-search">
      <i class="pi pi-search search-icon"></i>
      <input
        type="text"
        placeholder="Search patients, conditions, drugs, IDs..."
        class="search-input"
      />
    </div>

    <div class="header-actions">
      <div class="header-date">
        <span class="date-label">Today</span>
        <span class="date-value">{{ formattedDate }}</span>
      </div>
    </div>
  </header>
</template>

<script lang="ts" setup>
import { computed } from "vue";

const formattedDate = computed(() => {
  const now = new Date();
  return now.toLocaleDateString("en-US", {
    month: "long",
    day: "numeric",
    year: "numeric",
  });
});
</script>

<style scoped>
.app-header {
  background: white;
  border-bottom: 1px solid var(--slate-100);
  padding: 0.875rem 2rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: sticky;
  top: 0;
  z-index: 10;
  flex-shrink: 0;
}

.header-search {
  position: relative;
  width: 24rem;
}

.search-icon {
  position: absolute;
  left: 0.75rem;
  top: 50%;
  transform: translateY(-50%);
  color: var(--slate-400);
  font-size: 0.875rem;
  pointer-events: none;
}

.search-input {
  width: 100%;
  padding: 0.5rem 1rem 0.5rem 2.25rem;
  background: var(--slate-50);
  border: 1px solid var(--slate-200);
  border-radius: 8px;
  font-size: 0.8rem;
  color: var(--slate-700);
  outline: none;
  transition: all 0.2s;
}

.search-input:focus {
  border-color: var(--emerald-500);
  background: white;
  box-shadow: 0 0 0 3px rgba(5, 150, 105, 0.1);
}

.search-input::placeholder {
  color: var(--slate-400);
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.header-date {
  text-align: right;
  display: flex;
  flex-direction: column;
}

.date-label {
  font-size: 0.65rem;
  color: var(--slate-400);
  text-transform: uppercase;
  letter-spacing: 0.05em;
  font-weight: 600;
}

.date-value {
  font-size: 0.8rem;
  font-weight: 600;
  color: var(--slate-700);
}
</style>
