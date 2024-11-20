<!--
 Browse games page
 @author James Madden
-->
<script setup>
import { inject } from 'vue'
import StoreCounter from '@/components/StoreCounter.vue'

const { createThemeFromColour } = inject('theme')
// change to a red theme to match the holiday effect
createThemeFromColour('#FF9797')
</script>

<template>
  <main>
    <h1>Home</h1>
    <img class="logo" src="@/assets/logo.svg" width="64" />
    <StoreCounter />
  </main>
</template>

<style scoped>
.logo {
  view-transition-name: logo;
}
</style>
