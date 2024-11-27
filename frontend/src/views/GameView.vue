<script setup>
import { ref } from 'vue'

const props = defineProps({
  id: {
    type: String,
    required: true
  }
})

const game = ref(null)



// load the game
const loadGame = async () => {
  const response = await fetch(`http://localhost:8080/games/${props.id}`)
  game.value = await response.json()
}
loadGame()
</script>

<template>
  <div class="game-page">
    <img class="game-background" src="@/assets/loz-poster.jpg" />
    <header class="game-info">
      <img class="game-poster" src="https://upload.wikimedia.org/wikipedia/en/f/fb/The_Legend_of_Zelda_Tears_of_the_Kingdom_cover.jpg" />
      <div class="game-titles">
        <h1 class="header">{{ game ? game.name : "..." }}</h1>
        <h2 class="subheader">{{ game?.console }} • {{ game?.year }} • ${{ game?.price }} • rating</h2>
      </div>
    </header>
    <div class="game-bg-fade"></div>
  </div>
</template>

<style scoped>
.game-info {
  color: white;
  position: relative;
  z-index: 1;
  display: flex;
}
.game-poster {
  width: 200px;
  view-transition-name: game-cover;
}
.game-background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 512px;
  object-fit: cover;
  z-index: 0;
  object-position: 0% 75%;
}
.game-bg-fade {
  position: absolute;
  top: 128px;
  height: calc(100% - 128px);
  width: 100%;
  left: 0;
  background-color: rgba(17, 17, 17, 0.9);
  backdrop-filter: blur(64px);
  mask-image: linear-gradient(transparent, rgba(0, 0, 0, 0.75) 84px, rgba(0, 0, 0, 1) 168px);
}
</style>
