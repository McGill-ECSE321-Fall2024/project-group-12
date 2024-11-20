<script setup>
import StoreCounter from '@/components/StoreCounter.vue'
</script>

<template>
  <div class="about">
    <img src="@/assets/logo.svg" />
    <h1>About</h1>
    <StoreCounter />
  </div>
</template>

<style>
img {
  height: 100%;
  view-transition-name: logo;
}

@media (min-width: 1024px) {
  .about {
    min-height: 100%;
    display: flex;
    align-items: center;
  }
}
</style>
