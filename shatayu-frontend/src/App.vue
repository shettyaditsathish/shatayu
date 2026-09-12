<script setup>
import { RouterView } from "vue-router";
import HeaderView from "./components/HeaderView.vue";
import MainView from "./components/MainView.vue";
import SideBar from "./components/SideBar.vue";
</script>

<template>
  <div class="app-shell">
    <SideBar />
    <main class="app-main">
      <HeaderView />
      <MainView />
    </main>
  </div>
</template>

<style scoped>
.app-shell {
  display: flex;
  height: 100vh;
  width: 100%;
  overflow: hidden;
  background: var(--color-background);
}

.app-main {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-width: 0;
  overflow: hidden;
}
</style>
