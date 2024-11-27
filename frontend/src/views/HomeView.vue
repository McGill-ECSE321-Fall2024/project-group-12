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

// load all games from the db
const response = await fetch('http://localhost:8080/games')
const games = await response.json()
</script>

<template>
  <header>
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
  </header>
  <main>
    <h2 class="subheader">Browse Our Games</h2>
    <div class="game-grid">
      <GameCard v-for="game in games"
        :key="game.id"
        :id="game.id"
        :name="game.name"
        img="https://upload.wikimedia.org/wikipedia/en/f/fb/The_Legend_of_Zelda_Tears_of_the_Kingdom_cover.jpg"
      >

      </GameCard>
    </div>
  </main>
</template>

<style scoped>
h2 {
  color: white;
}
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
.game-grid {
  width: 100%;
  display: grid;
  grid-template-columns: repeat(auto-fill, 144px);
  grid-gap: 32px;
}
.game-grid .game-card {
  margin: 0;
}
</style>
