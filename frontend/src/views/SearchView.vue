<!--
 Browse games page
 @author James Madden
-->
<script setup>
import { inject } from 'vue'
import BackgroundGradient from '@/components/BackgroundGradient.vue'
import GamesList from '@/components/GamesList.vue'
import SearchBar from '@/components/SearchBar.vue'

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
  <SearchBar class="mobile-searchbar"></SearchBar>
  <GamesList :filter-from-search="true" />
</template>

<style scoped>
h2 {
  color: white;
}
.sale--text {
  color: #97b8ff;
  padding-bottom: 16px;
}
.mobile-searchbar {
  margin-bottom: 16px;
}

@media screen and (min-width: 601px) {
  .mobile-searchbar {
    display: none;
  }
}
</style>
