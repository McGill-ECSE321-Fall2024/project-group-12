<!--
 Browse games page
 @author James Madden
-->
<script setup>
import { inject } from 'vue'
import BackgroundGradient from '@/components/BackgroundGradient.vue'
import GamesList from '@/components/GamesList.vue'

const { createThemeFromColour } = inject('theme')
const { user } = inject('auth')

// change to a red theme to match the holiday effect
createThemeFromColour('#FF9797')
</script>

<template>
  <header>
    <BackgroundGradient colour="255, 151, 151" />
    <h1 class="header sale--text">
      Happy Holidays<span v-if="user != null"
        >, <span class="sale--name">{{ user?.name }}</span></span
      >!
    </h1>
    <h2 class="subheader sale--text">Enjoy up to 50% off some of the best games of the year!</h2>
  </header>
  <GamesList :filter-from-search="false" />
</template>

<style scoped>
h2 {
  color: white;
}
.logo {
  view-transition-name: logo;
}
.sale--text {
  color: #ff9797;
  padding-bottom: 16px;
}
.sale--name {
  color: white;
  font-weight: inherit;
}
.game-list {
  display: flex;
  padding-top: 8px;
  padding-left: 16px;
  padding-right: 16px;
  overflow-x: auto;
  overflow-y: visible;
  text-wrap: nowrap;
  position: relative;
  gap: 32px;
  left: -16px;
  width: calc(100% + 32px);
}
</style>
