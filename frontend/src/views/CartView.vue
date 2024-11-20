<script setup>
import { inject } from 'vue'
import StoreCounter from '@/components/StoreCounter.vue'

const { createThemeFromColour } = inject('theme')
// change to a red theme to match the holiday effect
createThemeFromColour('#00ff00')
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
