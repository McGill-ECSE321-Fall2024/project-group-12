<script setup>
import FancyButton from '@/components/FancyButton.vue'
import GameReviews from '@/components/GameReviews.vue'
import PlusIcon from 'vue-material-design-icons/Plus.vue'
import HeartOutlineIcon from 'vue-material-design-icons/HeartOutline.vue'
import { ref, useTemplateRef, onMounted, inject } from 'vue'

const props = defineProps({
  id: {
    type: String,
    required: true,
  },
})

const { createThemeFromImg, createThemeFromColour } = inject('theme')
const { user, token } = inject('auth')

// pictures for the game
const posterImgUrl = ref("")
const backgroundImgUrl = ref("")

const game = ref(null)

// set the default theme to a grey while the theme colour is loaded
createThemeFromColour('#999999');

const backgroundImg = useTemplateRef('background-img')

// load the images
async function loadImages() {

  // do both API calls at once
  const responses = await Promise.all([
    fetch(`http://localhost:8080/games/${props.id}/cover`),
    fetch(`http://localhost:8080/games/${props.id}/background`)
  ])
  // convert to JSON
  const images = await Promise.all(responses.map(resp => resp.json()))

  // set the URLs
  posterImgUrl.value = `data:image/${images[0].type};base64,${images[0].image}`
  backgroundImgUrl.value = `data:image/${images[1].type};base64,${images[1].image}`

  // set the colour scheme
  backgroundImg.value.addEventListener('load', () => {
    setTimeout(() => {
      createThemeFromImg(backgroundImg.value)
    }, 300)
  })

}

loadImages()

// load the game
const loadGame = async () => {
  const response = await fetch(`http://localhost:8080/games/${props.id}`)
  game.value = await response.json()
}
loadGame()

// provide methods for button presses
const addToCart = async () => {
  // send the post request to add to cart
  console.log(user.value)
  const response = await fetch(`http://localhost:8080/cart/${user.value?.cart?.id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token.value}`,
    },
    body: JSON.stringify({
      gameId: props.id,
    }),
  })
  if (response.ok) {
    alert('added to cart')
  }
}
const addToWishlist = async () => {
  // send the post request to add to cart
  console.log(user.value)
  const response = await fetch(`http://localhost:8080/wishlist/${user.value?.wishlist?.id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token.value}`,
    },
    body: JSON.stringify({
      gameId: props.id,
    }),
  })
  if (response.ok) {
    alert('added to wishlist')
  }
}
</script>

<template>
  <div class="game-page">
    <img class="game-background" :src="backgroundImgUrl" ref="background-img" />
    <header class="game-info">
      <img
        class="game-poster"
        :src="posterImgUrl"
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
        <FancyButton filled label="Add to Cart" @click="addToCart">
          <PlusIcon />
        </FancyButton>
        <FancyButton label="Add to Wishlist" @click="addToWishlist">
          <HeartOutlineIcon />
        </FancyButton>
      </div>
      <h2>Reviews</h2>
      <!-- provide a fallback loading spinner while reviews load -->
      <Suspense>
        <GameReviews :id="id"></GameReviews>

        <template #fallback>
          <p>Loading...</p>
        </template>
      </Suspense>
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
  height: 277px;
  object-fit: cover;
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
