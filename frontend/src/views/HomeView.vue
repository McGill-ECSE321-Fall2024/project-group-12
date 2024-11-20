<script setup>
import StoreCounter from '@/components/StoreCounter.vue'
</script>

<template>
  <main>
    <h1>Home</h1>
    <img src="@/assets/logo.svg" width="64" />
    <StoreCounter />
  </main>
</template>

<style scoped>
img {
  view-transition-name: logo;
}
</style>
