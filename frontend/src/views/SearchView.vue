<!--
 Browse games page
 @author James Madden
-->
<script setup>
import { inject } from 'vue'
import BackgroundGradient from '@/components/BackgroundGradient.vue'
import GamesList from '@/components/GamesList.vue'

const { createThemeFromColour } = inject('theme')

// change to a blue theme
createThemeFromColour('#97B8FF')
console.log('updated theme')
</script>

<template>
  <header>
    <BackgroundGradient colour="151, 184, 255" />
    <h1 class="header sale--text">Search</h1>
  </header>
  <GamesList :filter-from-search="false" />
</template>

<style scoped>
h2 {
  color: white;
}
.sale--text {
  color: #97b8ff;
  padding-bottom: 16px;
}
</style>
