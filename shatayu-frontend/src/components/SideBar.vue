<template>
  <div class="h-100 card m-0 p-0">
    <nav class="sidebar h-100 w-100">
      <ul>
        <li
          v-for="item in navItems"
          :key="item.name"
          :class="{ active: activeItem === item.name }"
          @click="activeItem = item.name"
        >
          <RouterLink :to="item.path"> {{ item.label }} </RouterLink>
        </li>
      </ul>
    </nav>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { RouterLink } from "vue-router";

const navItems = [
  { name: "dashboard", label: "Dashboard", path: "/" },
  { name: "patients", label: "Patients", path: "/patients" },
  { name: "panchakarma", label: "Panchakarma", path: "/panchkarma" },
];

const activeItem = ref("dashboard");
</script>

<style scoped>
.sidebar {
  width: 200px;
  background: #f5f5f5;
  padding: 1rem 0;
}
ul {
  list-style: none;
  padding: 0;
  margin: 0;
}
li {
  padding: 12px 24px;
  cursor: pointer;
  color: #333;
  transition: background 0.2s, color 0.2s;
}
li.active {
  background: #1976d2;
  color: #fff;
  border-radius: 4px;
  font-weight: bold;
}
li:not(.active):hover {
  background: #e3eafc;
}
</style>
