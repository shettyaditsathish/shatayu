<template>
  <div class="main-content">
    <div class="content-area">
      <RouterView />
    </div>
  </div>
</template>

<script lang="ts" setup>
import { RouterView } from "vue-router";
</script>

<style scoped>
.main-content {
  flex: 1;
  overflow-y: auto;
  padding: 1.5rem 2rem;
}

.content-area {
  max-width: 1400px;
  width: 100%;
  margin: 0 auto;
}
</style>
