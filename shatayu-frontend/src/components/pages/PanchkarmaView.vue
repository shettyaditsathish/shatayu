<template>
  <div class="panchkarma-page">
    <!-- Page Header -->
    <div class="page-header-card">
      <div>
        <h3>Panchakarma Center</h3>
        <p>Detox and purification therapy schedules & room allocations</p>
      </div>
    </div>

    <!-- Placeholder Content -->
    <div class="placeholder-card">
      <div class="placeholder-icon">
        <i class="pi pi-sparkles"></i>
      </div>
      <h4>Panchakarma Sessions</h4>
      <p>Manage patient Panchakarma therapy sessions, scheduling, and progress tracking from this section.</p>
    </div>
  </div>
</template>

<script lang="ts" setup></script>

<style scoped>
.panchkarma-page {
  display: flex;
  flex-direction: column;
  gap: 1.25rem;
}

.page-header-card {
  background: white;
  padding: 1.5rem;
  border-radius: var(--radius-2xl);
  border: 1px solid var(--slate-100);
  box-shadow: var(--shadow-sm);
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.page-header-card h3 {
  font-size: 1.25rem;
  font-weight: 700;
  color: var(--slate-900);
  margin: 0;
}

.page-header-card p {
  font-size: 0.8rem;
  color: var(--slate-400);
  margin: 0.25rem 0 0;
}

.placeholder-card {
  background: white;
  border-radius: var(--radius-2xl);
  border: 1px solid var(--slate-100);
  box-shadow: var(--shadow-sm);
  padding: 3rem;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.75rem;
}

.placeholder-icon {
  width: 64px;
  height: 64px;
  border-radius: 16px;
  background: var(--emerald-50);
  color: var(--emerald-600);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.75rem;
  margin-bottom: 0.5rem;
}

.placeholder-card h4 {
  font-size: 1.1rem;
  font-weight: 700;
  color: var(--slate-900);
  margin: 0;
}

.placeholder-card p {
  font-size: 0.85rem;
  color: var(--slate-400);
  max-width: 28rem;
  margin: 0;
  line-height: 1.5;
}
</style>
