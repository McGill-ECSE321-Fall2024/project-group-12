<!--
 Browse games page
 @author James Madden
-->
<script setup>
import { inject } from 'vue'
import BackgroundGradient from '@/components/BackgroundGradient.vue'

const { createThemeFromColour } = inject('theme')
const { user } = inject('auth')
// change to a red theme to match the holiday effect
createThemeFromColour('#FF9797')
</script>

<template>
  <main>
    <BackgroundGradient colour="255, 151, 151" />
    <h1 class="header sale--text">Happy Holidays, <span class="sale--name">{{ user?.name }}!</span></h1>
    <h2 class="subheader sale--text">Enjoy up to 50% off some of the best games of the year!</h2>
  </main>
</template>

<style scoped>
.logo {
  view-transition-name: logo;
}
.sale--text {
  color: #FF9797;
}
.sale--name {
  color: white;
  font-weight: inherit;
}
</style>
