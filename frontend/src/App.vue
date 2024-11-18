<script setup>
import { RouterView } from 'vue-router'
import NavBar from './components/NavBar.vue'
</script>

<template>
  <NavBar />

  <main class="page-container">
    <RouterView />
  </main>
</template>

<style scoped>
.page-container {
  height: calc(100vh - var(--navbar-height));
  background: var(--color-background-soft);
  overflow: auto;
  border-top-left-radius: 24px;
  border-top-right-radius: 24px;
  padding: 1rem;
}
</style>
