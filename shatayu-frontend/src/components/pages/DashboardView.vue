<template>
  <div class="dashboard">
    <!-- Welcome Banner -->
    <div class="welcome-banner">
      <div class="welcome-content">
        <div class="welcome-badge">
          <i class="pi pi-sparkles"></i>
          <span>Ayurvedic Holistic Care</span>
        </div>
        <h2>Shatayu Health Management Portal</h2>
        <p>
          Manage appointments, administer customized Panchakarma sessions,
          track patient history, and direct pharmacy storage.
        </p>
      </div>
      <div class="welcome-decoration">
        <i class="pi pi-heart-fill"></i>
      </div>
    </div>

    <!-- Quick Stats -->
    <div class="stats-grid">
      <div class="stat-card">
        <div class="stat-content">
          <span class="stat-label">Patients</span>
          <h3 class="stat-value">--</h3>
          <span class="stat-hint">Search to view records</span>
        </div>
        <div class="stat-icon">
          <i class="pi pi-users"></i>
        </div>
      </div>

      <div class="stat-card">
        <div class="stat-content">
          <span class="stat-label">Panchakarma</span>
          <h3 class="stat-value">Active</h3>
          <span class="stat-hint">View sessions in center</span>
        </div>
        <div class="stat-icon">
          <i class="pi pi-sparkles"></i>
        </div>
      </div>

      <div class="stat-card">
        <div class="stat-content">
          <span class="stat-label">Pharmacy</span>
          <h3 class="stat-value">Aushadhi</h3>
          <span class="stat-hint">Manage drug inventory</span>
        </div>
        <div class="stat-icon icon-amber">
          <i class="pi pi-box"></i>
        </div>
      </div>

      <div class="stat-card">
        <div class="stat-content">
          <span class="stat-label">Quick Action</span>
          <h3 class="stat-value clickable" @click="$router.push('/addPatient')">+ New Patient</h3>
          <span class="stat-hint">Register a new patient</span>
        </div>
        <div class="stat-icon icon-emerald">
          <i class="pi pi-user-plus"></i>
        </div>
      </div>
    </div>

    <!-- Info Card -->
    <div class="info-card">
      <i class="pi pi-heart-fill info-icon"></i>
      <p>
        Welcome to <strong>Shatayu Clinic Management System</strong>. Use the sidebar to navigate
        between patient records, treatments, Panchakarma sessions, and the pharmacy inventory.
      </p>
    </div>
  </div>
</template>

<script lang="ts" setup></script>

<style scoped>
.dashboard {
  display: flex;
  flex-direction: column;
  gap: 1.25rem;
}

/* Welcome Banner */
.welcome-banner {
  background: linear-gradient(135deg, #065f46 0%, #0f766e 100%);
  color: white;
  padding: 2rem;
  border-radius: var(--radius-2xl);
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: relative;
  overflow: hidden;
}

.welcome-content {
  z-index: 1;
}

.welcome-badge {
  display: inline-flex;
  align-items: center;
  gap: 0.375rem;
  background: rgba(255, 255, 255, 0.12);
  color: var(--emerald-200);
  padding: 0.375rem 0.75rem;
  border-radius: 999px;
  font-size: 0.7rem;
  font-weight: 600;
  backdrop-filter: blur(8px);
  margin-bottom: 0.75rem;
}

.welcome-content h2 {
  font-size: 1.5rem;
  font-weight: 700;
  letter-spacing: -0.02em;
  margin: 0 0 0.375rem;
}

.welcome-content p {
  font-size: 0.85rem;
  color: var(--emerald-100);
  max-width: 32rem;
  margin: 0;
  line-height: 1.5;
}

.welcome-decoration {
  position: absolute;
  right: 2.5rem;
  bottom: -1.5rem;
  opacity: 0.08;
  font-size: 10rem;
}

/* Stats Grid */
.stats-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 1.25rem;
}

.stat-card {
  background: white;
  padding: 1.25rem;
  border-radius: var(--radius-2xl);
  border: 1px solid var(--slate-100);
  box-shadow: var(--shadow-sm);
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.stat-content {
  display: flex;
  flex-direction: column;
  gap: 0.125rem;
}

.stat-label {
  font-size: 0.65rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.05em;
  color: var(--slate-400);
}

.stat-value {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--slate-900);
  margin: 0;
}

.stat-value.clickable {
  color: var(--emerald-600);
  cursor: pointer;
  font-size: 1.1rem;
}

.stat-value.clickable:hover {
  color: var(--emerald-700);
}

.stat-hint {
  font-size: 0.7rem;
  color: var(--slate-400);
}

.stat-icon {
  padding: 0.75rem;
  background: var(--slate-50);
  color: var(--emerald-600);
  border-radius: 12px;
  font-size: 1.25rem;
}

.stat-icon.icon-amber {
  color: #d97706;
  background: #fffbeb;
}

.stat-icon.icon-emerald {
  color: var(--emerald-600);
  background: var(--emerald-50);
}

/* Info Card */
.info-card {
  background: var(--emerald-50);
  border: 1px solid var(--emerald-100);
  border-radius: var(--radius-xl);
  padding: 1rem 1.25rem;
  display: flex;
  align-items: flex-start;
  gap: 0.75rem;
}

.info-icon {
  color: var(--emerald-600);
  font-size: 1rem;
  flex-shrink: 0;
  margin-top: 0.125rem;
}

.info-card p {
  font-size: 0.8rem;
  color: var(--emerald-800);
  margin: 0;
  line-height: 1.5;
}

@media (max-width: 1024px) {
  .stats-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>
