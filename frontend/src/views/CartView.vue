<script setup>
import StoreCounter from '@/components/StoreCounter.vue'
</script>

<template>
  <div class="cart">
    <img src="@/assets/logo.svg" />
    <h1>Cart</h1>
    <StoreCounter />
  </div>
</template>

<style scoped>
img {
  height: 100%;
  view-transition-name: logo;
}
</style>
