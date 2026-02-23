<template>
  <nav
    data-v-b1bcf79c=""
    data-v-071eda8c=""
    class="navbar navbar-expand-lg navbar-light appbar-header"
    aria-label="Main navigation"
  >
    <button
      data-v-b1bcf79c=""
      class="p-button p-component p-button-primary p-button-outlined ms-2"
      data-p="outlined"
      type="button"
      data-pc-name="button"
      data-p-disabled="false"
      data-p-severity="primary"
      data-bs-toggle="tooltip"
      data-bs-placement="bottom"
      title="Toggle Navigation"
      pc5=""
      data-pc-section="root"
    >
      <i data-v-b1bcf79c="" class="pi pi-bars"></i>
    </button>

    <div data-v-b1bcf79c="" class="ms-3 me-4 divider">
      <div class="logo mr-auto">
        <h1 class="text-light">
          <a href="index.html"><span>S</span>hatayu</a>
        </h1>
      </div>
    </div>
    <!---->
    <div data-v-b1bcf79c="" class="col"></div>
    <div data-v-b1bcf79c="" class="col" id="navbarsExampleDefault">
      <div data-v-b1bcf79c="" class="d-flex justify-content-end">
        <div data-v-b1bcf79c="" class="grid me-2 mt-3">
          <span data-v-b1bcf79c=""><b data-v-b1bcf79c="">Shatayu</b></span>
        </div>
        <div data-v-b1bcf79c="" class="dropdown" style="margin-right: 1.5rem">
          <span data-v-b1bcf79c="" class="badge rounded-pill bg-light"></span>
        </div>
        <!---->
        <form data-v-b1bcf79c="" class="mt-2">
          <Button
            data-v-b1bcf79c=""
            class="p-button p-component p-button-primary p-button-outlined mt-1 me-2"
            data-p="outlined"
            type="submit"
            data-pc-name="button"
            data-p-disabled="false"
            data-p-severity="primary"
            pc8=""
            data-pc-section="root"
          >
            <i data-v-b1bcf79c="" class="c"></i> Sign Out
          </Button>
        </form>
      </div>
    </div>
  </nav>
</template>
<script lang="ts" setup>
import { Button } from "primevue";
</script>
