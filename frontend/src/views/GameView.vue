<script setup>
import FancyButton from '@/components/FancyButton.vue'
import GameReviews from '@/components/GameReviews.vue'
import StarRating from '@/components/StarRating.vue'
import PlusIcon from 'vue-material-design-icons/Plus.vue'
import CheckIcon from 'vue-material-design-icons/CheckBold.vue'
import HeartOutlineIcon from 'vue-material-design-icons/HeartOutline.vue'
import { ref, useTemplateRef, inject, watch } from 'vue'

const props = defineProps({
  id: {
    type: String,
    required: true,
  },
})

const { createThemeFromImg, createThemeFromColour } = inject('theme')
const { user, token, loadUser } = inject('auth')

// store whether or not the game is in the user's cart and wishlist
const inCart = ref(false)
const inWishlist = ref(false)

// check if a game with this id is in the user's cart or wishlist
const checkInCart = () => {
  if (user.value != null) {
    inCart.value = false
    inWishlist.value = false
    user.value.cart.games.forEach((game) => {
      if (game.id == props.id) inCart.value = true
    })
    user.value.wishlist.games.forEach((game) => {
      if (game.id == props.id) inWishlist.value = true
    })
  }
}

checkInCart()
loadUser()

// if user updates (aka reloaded cart changes), rerun checking whether game is in wishlist or cart
watch(user, () => {
  console.log('watch detected a change in user')
  checkInCart()
})

// pictures for the game
const posterImgUrl = ref('')
const backgroundImgUrl = ref('')

const game = ref(null)
const rating = ref(null)

// set the default theme to a grey while the theme colour is loaded
createThemeFromColour('#999999')

const backgroundImg = useTemplateRef('background-img')

// load the images
async function loadImages() {
  // do both API calls at once
  const responses = await Promise.all([
    fetch(`http://localhost:8080/games/${props.id}/cover`),
    fetch(`http://localhost:8080/games/${props.id}/background`),
  ])
  // convert to JSON
  const images = await Promise.all(responses.map((resp) => resp.json()))

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
  // and get the rating for the game
  const ratingResp = await fetch(`http://localhost:8080/games/${props.id}/rating`)
  rating.value = new Number(await ratingResp.text()) + 0
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
    inCart.value = true
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
    inWishlist.value = true
  }
}
</script>

<template>
  <div class="game-page">
    <img class="game-background" :src="backgroundImgUrl" ref="background-img" />
    <header class="game-info">
      <img class="game-poster" :src="posterImgUrl" />
      <div class="game-titles">
        <h1 class="header">{{ game ? game.name : '...' }}</h1>
        <h2 class="subheader">
          {{ game?.console }} • {{ game?.year }} • ${{ game?.price }} •
          <StarRating v-if="rating != null" :rating="rating"></StarRating>
        </h2>
      </div>
    </header>

    <!-- the background fade of the page -->
    <div class="game-bg-fade"></div>

    <main class="game-content">
      <div v-if="user != null" class="button-row">
        <FancyButton
          filled
          :disabled="inCart"
          :label="inCart ? 'Added to Cart' : 'Add to Cart'"
          @click="addToCart"
        >
          <CheckIcon v-if="inCart" />
          <PlusIcon v-else />
        </FancyButton>
        <FancyButton
          :disabled="inWishlist"
          :label="inWishlist ? 'Added to Wishlist' : 'Add to Wishlist'"
          @click="addToWishlist"
        >
          <CheckIcon v-if="inWishlist" />
          <HeartOutlineIcon v-else />
        </FancyButton>
      </div>
      <h2 v-else>Sign in to add to cart or wishlist.</h2>
      <h1>Reviews</h1>
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
.game-page {
  margin-top: 160px;
}
.game-info {
  color: white;
  position: relative;
  z-index: 1;
  display: flex;
  padding: 0 16px;
  align-items: end;
  gap: 28px;
}

.game-content {
  position: relative;
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
  background-color: rgba(34, 34, 34, 0.9);
  backdrop-filter: blur(64px);
  mask-image: linear-gradient(transparent, rgba(0, 0, 0, 0.75) 84px, rgba(0, 0, 0, 1) 168px);
}

/* adapt to a mobile layout */
@media only screen and (max-width: 600px) {
  .game-info {
    flex-direction: column;
    align-items: center;
  }
  .game-poster {
    width: 132px;
    height: 183px;
  }
  .header {
    font-size: 32px;
    line-height: 32px;
    text-align: center;
  }
  .subheader {
    font-size: 20px;
    text-align: center;
  }
}
</style>
