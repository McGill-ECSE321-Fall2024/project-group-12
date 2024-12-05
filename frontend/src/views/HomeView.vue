<!--
 Browse games page
 @author James Madden
-->
<script setup>
import { inject, ref, nextTick } from 'vue'
import BackgroundGradient from '@/components/BackgroundGradient.vue'
import GameCard from '@/components/GameCard.vue'
import FancyChips from '@/components/FancyChips.vue'

const { createThemeFromColour } = inject('theme')
const { user } = inject('auth')

// change to a red theme to match the holiday effect
createThemeFromColour('#FF9797')

// the filterable conditions
const consoleFilters = ['PlayStation', 'XBox', 'PC', 'Switch']
const genreFilters = ['Action', 'Adventure', 'Sports', 'Puzzle']
const enabledConsoleFilter = ref(null)
const enabledGenreFilter = ref(null)

// load all games from the db
const response = await fetch('http://localhost:8080/games?status=InCatalog')
const games = await response.json()

const setConsoleFilter = newFilter => {
  if (document.startViewTransition) {
    document.startViewTransition(async () => {
      enabledConsoleFilter.value = newFilter
      await nextTick()
    })
  } else {
    enabledConsoleFilter.value = newFilter
  }
}
const setGenreFilter = newFilter => {
  if (document.startViewTransition) {
    document.startViewTransition(async () => {
      enabledGenreFilter.value = newFilter
      await nextTick()
    })
  } else {
    enabledGenreFilter.value = newFilter
  }
}

// if filters are applied, only show the matching games
const filterGames = () => {
  let filteredGames = games
  // filter the list, if filters are on
  if (enabledConsoleFilter.value != null) {
    filteredGames = filteredGames.filter(game => game.console == enabledConsoleFilter.value)
  }
  if (enabledGenreFilter.value != null) {
    filteredGames = filteredGames.filter(game => game.category == enabledGenreFilter.value)
  }
  return filteredGames
}
</script>

<template>
  <header>
    <BackgroundGradient colour="255, 151, 151" />
    <h1 class="header sale--text">
      Happy Holidays<span v-if="user != null">, <span class="sale--name">{{ user?.name }}</span></span>!
    </h1>
    <h2 class="subheader sale--text">Enjoy up to 50% off some of the best games of the year!</h2>
  </header>
  <main>
    <FancyChips :options="[ consoleFilters, genreFilters ]" :callbacks="[setConsoleFilter, setGenreFilter]"></FancyChips>
    <div class="game-grid">
      <GameCard
        v-for="game in filterGames()"
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
