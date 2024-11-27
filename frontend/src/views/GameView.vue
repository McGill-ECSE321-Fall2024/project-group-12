<script setup>
import FancyButton from '@/components/FancyButton.vue'
import PlusIcon from 'vue-material-design-icons/Plus.vue'
import HeartOutlineIcon from 'vue-material-design-icons/HeartOutline.vue'
import { ref, useTemplateRef, onMounted, inject } from 'vue'

const props = defineProps({
  id: {
    type: String,
    required: true,
  },
})

const { createThemeFromImg } = inject('theme')

const game = ref(null)

// set the colour scheme
const backgroundImg = useTemplateRef('background-img')
onMounted(() => {
  createThemeFromImg(backgroundImg.value)
})

// load the game
const loadGame = async () => {
  const response = await fetch(`http://localhost:8080/games/${props.id}`)
  game.value = await response.json()
}
loadGame()
</script>

<template>
  <div class="game-page">
    <img class="game-background" src="@/assets/loz-poster.jpg" ref="background-img" />
    <header class="game-info">
      <img
        class="game-poster"
        src="https://upload.wikimedia.org/wikipedia/en/f/fb/The_Legend_of_Zelda_Tears_of_the_Kingdom_cover.jpg"
      />
      <div class="game-titles">
        <h1 class="header">{{ game ? game.name : '...' }}</h1>
        <h2 class="subheader">
          {{ game?.console }} • {{ game?.year }} • ${{ game?.price }} • rating
        </h2>
      </div>
    </header>

    <!-- the background fade of the page -->
    <div class="game-bg-fade"></div>

    <main class="game-content">
      <div class="button-row">
        <FancyButton filled label="Add to Cart">
          <PlusIcon />
        </FancyButton>
        <FancyButton label="Add to Wishlist">
          <HeartOutlineIcon />
        </FancyButton>
      </div>
    </main>
  </div>
</template>

<style scoped>
.game-info {
  color: white;
  position: relative;
  z-index: 1;
  display: flex;
  padding: 0 16px;
  top: 160px;
  align-items: end;
  gap: 28px;
}

.game-content {
  position: relative;
  top: 160px;
}

.game-poster {
  width: 200px;
  view-transition-name: game-cover;
}

.button-row {
  display: flex;
  gap: 16px;
  padding: 16px;
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
