<template>
  <aside class="sidebar">
    <!-- Branding -->
    <div class="sidebar-brand">
      <div class="brand-icon">
        <i class="pi pi-heart-fill"></i>
      </div>
      <div class="brand-text">
        <h1>Shatayu</h1>
        <span>Ayurveda Excellence</span>
      </div>
    </div>

    <!-- Navigation -->
    <nav class="sidebar-nav">
      <RouterLink
        v-for="item in navItems"
        :key="item.name"
        :to="item.path"
        class="nav-item"
        :class="{ active: activeItem === item.name }"
        @click="activeItem = item.name"
      >
        <i :class="item.icon"></i>
        <span>{{ item.label }}</span>
      </RouterLink>
    </nav>

    <!-- User Info -->
    <div class="sidebar-footer">
      <div class="user-info">
        <div class="user-avatar">Dr</div>
        <div class="user-details">
          <span class="user-greeting">Welcome back,</span>
          <span class="user-name">Dr. Bhagyashree Patil</span>
        </div>
      </div>
      <button class="sign-out-btn">
        <i class="pi pi-sign-out"></i>
        <span>Sign Out</span>
      </button>
    </div>
  </aside>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { RouterLink } from "vue-router";

const navItems = [
  { name: "dashboard", label: "Dashboard", path: "/", icon: "pi pi-th-large" },
  {
    name: "patients",
    label: "Patients Directory",
    path: "/patients",
    icon: "pi pi-users",
  },
  {
    name: "addPatient",
    label: "Add New Patient",
    path: "/addPatient",
    icon: "pi pi-user-plus",
  },
  {
    name: "panchakarma",
    label: "Panchakarma Center",
    path: "/panchkarma",
    icon: "pi pi-sparkles",
  },
  {
    name: "drugs",
    label: "Aushadhi Pharmacy",
    path: "/drugs",
    icon: "pi pi-box",
  },
  {
    name: "treatmentByDate",
    label: "Treatment By Date",
    path: "/treatmentByDate",
    icon: "pi pi-calendar",
  },
];

const activeItem = ref("dashboard");
</script>

<style scoped>
.sidebar {
  width: var(--sidebar-width);
  min-width: var(--sidebar-width);
  background: var(--sidebar-bg);
  color: var(--sidebar-text);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  border-right: 1px solid var(--sidebar-border);
  height: 100vh;
  overflow: hidden;
}

/* Branding */
.sidebar-brand {
  padding: 1.5rem;
  border-bottom: 1px solid var(--sidebar-border);
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.brand-icon {
  background: var(--emerald-600);
  padding: 0.5rem;
  border-radius: 10px;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.25rem;
}

.brand-text h1 {
  font-size: 1.25rem;
  font-weight: 700;
  color: white;
  letter-spacing: -0.01em;
  margin: 0;
  line-height: 1.2;
}

.brand-text span {
  font-size: 0.7rem;
  color: var(--emerald-400);
  font-weight: 500;
}

/* Navigation */
.sidebar-nav {
  padding: 1rem;
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
  flex: 1;
  overflow-y: auto;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.75rem 1rem;
  border-radius: 10px;
  font-size: 0.875rem;
  font-weight: 500;
  color: var(--sidebar-text);
  text-decoration: none;
  transition: all 0.2s ease;
  cursor: pointer;
}

.nav-item:hover {
  background: var(--sidebar-hover);
  color: white;
}

.nav-item.active {
  background: var(--sidebar-active);
  color: white;
  font-weight: 600;
  box-shadow: 0 4px 12px rgba(5, 150, 105, 0.3);
}

.nav-item i {
  font-size: 1rem;
  width: 20px;
  text-align: center;
}

/* Footer */
.sidebar-footer {
  padding: 1rem;
  border-top: 1px solid var(--sidebar-border);
}

.user-info {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.625rem;
  background: rgba(30, 41, 59, 0.5);
  border-radius: 10px;
  margin-bottom: 0.75rem;
}

.user-avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background: rgba(16, 185, 129, 0.15);
  border: 1px solid rgba(16, 185, 129, 0.25);
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--emerald-400);
  font-weight: 700;
  font-size: 0.8rem;
  flex-shrink: 0;
}

.user-details {
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.user-greeting {
  font-size: 0.65rem;
  color: var(--slate-400);
}

.user-name {
  font-size: 0.8rem;
  font-weight: 600;
  color: white;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.sign-out-btn {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  padding: 0.625rem;
  border-radius: 10px;
  border: 1px solid var(--sidebar-border);
  background: transparent;
  color: var(--sidebar-text);
  font-size: 0.8rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
}

.sign-out-btn:hover {
  border-color: rgba(239, 68, 68, 0.3);
  background: rgba(239, 68, 68, 0.08);
  color: #f87171;
}
</style>
