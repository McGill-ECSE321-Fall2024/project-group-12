<!--
 Browse games page
 @author James Madden
-->
<script setup>
import { inject } from 'vue'
import BackgroundGradient from '@/components/BackgroundGradient.vue'
import GameCard from '@/components/GameCard.vue'

const { createThemeFromColour } = inject('theme')
const { user } = inject('auth')
// change to a red theme to match the holiday effect
createThemeFromColour('#FF9797')
</script>

<template>
  <main>
    <BackgroundGradient colour="255, 151, 151" />
    <h1 class="header sale--text">Happy Holidays, <span class="sale--name">{{ user?.name }}</span>!</h1>
    <h2 class="subheader sale--text">Enjoy up to 50% off some of the best games of the year!</h2>

    <div class="game-list">
      <GameCard id="0" name="Legend of Zelda: Tears of the Kingd..." img="https://upload.wikimedia.org/wikipedia/en/f/fb/The_Legend_of_Zelda_Tears_of_the_Kingdom_cover.jpg" />
      <GameCard id="0" name="Mario Kart 8" img="https://upload.wikimedia.org/wikipedia/en/b/b5/MarioKart8Boxart.jpg" />
      <GameCard id="0" name="Animal Crossing: New Horizons" img="https://upload.wikimedia.org/wikipedia/en/1/1f/Animal_Crossing_New_Horizons.jpg" />
      <GameCard id="0" name="Legend of Zelda: Tears of the Kingd..." img="https://upload.wikimedia.org/wikipedia/en/f/fb/The_Legend_of_Zelda_Tears_of_the_Kingdom_cover.jpg" />
      <GameCard id="0" name="Mario Kart 8" img="https://upload.wikimedia.org/wikipedia/en/b/b5/MarioKart8Boxart.jpg" />
      <GameCard id="0" name="Animal Crossing: New Horizons" img="https://upload.wikimedia.org/wikipedia/en/1/1f/Animal_Crossing_New_Horizons.jpg" />
      <GameCard id="0" name="Legend of Zelda: Tears of the Kingd..." img="https://upload.wikimedia.org/wikipedia/en/f/fb/The_Legend_of_Zelda_Tears_of_the_Kingdom_cover.jpg" />
      <GameCard id="0" name="Mario Kart 8" img="https://upload.wikimedia.org/wikipedia/en/b/b5/MarioKart8Boxart.jpg" />
      <GameCard id="0" name="Animal Crossing: New Horizons" img="https://upload.wikimedia.org/wikipedia/en/1/1f/Animal_Crossing_New_Horizons.jpg" />
    </div>
  </main>
</template>

<style scoped>
.logo {
  view-transition-name: logo;
}
.sale--text {
  color: #FF9797;
  padding-bottom: 16px;
}
.sale--name {
  color: white;
  font-weight: inherit;
}
.game-list {
  padding-top: 8px;
  overflow-x: auto;
  overflow-y: visible;
  text-wrap: nowrap;
  position: relative;
  left: -16px;
  width: calc(100% + 32px);
}
</style>
