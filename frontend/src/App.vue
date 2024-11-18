<script setup>
import { RouterView } from 'vue-router'
import NavBar from './components/NavBar.vue'
</script>

<template>
  <div class="app-root">
    <NavBar />

    <main class="page-container">
      <RouterView />
    </main>
  </div>
</template>

<style scoped>
.page-container {
  position: relative;
  height: calc(100vh - var(--navbar-height));
  background: var(--color-background-soft);
  overflow: auto;
  border-top-left-radius: 24px;
  border-top-right-radius: 24px;
  padding: 1rem;
}

/**
 * adapt the navbar to a mobile device
 */
@media only screen and (max-width: 600px) {
  .app-root {
    display: flex;
    flex-direction: column-reverse;
  }
  .page-container {
    border-top-left-radius: 0px;
    border-top-right-radius: 0px;
    border-bottom-left-radius: 24px;
    border-bottom-right-radius: 24px;
  }
}
</style>
