<!-- a component used to display and browse all games, used both on the homepage and the games page -->
<script setup>
import { ref, nextTick, watch } from 'vue'
import GameCard from '@/components/GameCard.vue'
import FancyChips from '@/components/FancyChips.vue'
import store from '@/store.js'

const props = defineProps({
  filterFromSearch: Boolean,
})

if (props.filterFromSearch) {
  watch(store, () => {
    // update the filtered games
  })
}

// the filterable conditions
const consoleFilters = ['PlayStation', 'XBox', 'PC', 'Switch']
const genreFilters = ['Action', 'Adventure', 'Sports', 'Puzzle']
const enabledConsoleFilter = ref(null)
const enabledGenreFilter = ref(null)

// load all games from the db
const response = await fetch('http://localhost:8080/games?status=InCatalog')
const games = await response.json()

const setConsoleFilter = (newFilter) => {
  if (document.startViewTransition) {
    document.startViewTransition(async () => {
      enabledConsoleFilter.value = newFilter
      await nextTick()
    })
  } else {
    enabledConsoleFilter.value = newFilter
  }
}
const setGenreFilter = (newFilter) => {
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
  if (props.filterFromSearch) {
    const regex = new RegExp(store.searchQuery, 'i')
    filteredGames = filteredGames.filter(
      (game) => regex.test(game.name) || regex.test(game.description),
    )
  }
  if (enabledConsoleFilter.value != null) {
    filteredGames = filteredGames.filter((game) => game.console == enabledConsoleFilter.value)
  }
  if (enabledGenreFilter.value != null) {
    filteredGames = filteredGames.filter((game) => game.category == enabledGenreFilter.value)
  }
  return filteredGames
}
</script>

<template>
  <main>
    <FancyChips
      :options="[consoleFilters, genreFilters]"
      :callbacks="[setConsoleFilter, setGenreFilter]"
    ></FancyChips>
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
.game-grid {
  width: 100%;
  display: grid;
  grid-template-columns: repeat(auto-fill, 144px);
  grid-gap: 32px;
  justify-content: center;
}
.game-grid .game-card {
  margin: 0;
}
</style>
