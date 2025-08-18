<script setup>
import HeaderView from "./components/HeaderView.vue";
import MainView from "./components/MainView.vue";
import SideBar from "./components/SideBar.vue";
</script>

<template>
  <HeaderView style="height: 7%"></HeaderView>
  <div class="row w-100 h-90" style="height: 93%">
    <SideBar class="col-2"></SideBar>
    <MainView class="col-10"></MainView>
  </div>
</template>

<style scoped>
header {
  line-height: 1.5;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }
}
</style>
