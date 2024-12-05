<script setup>
import { inject, ref } from 'vue'
import PlusIcon from 'vue-material-design-icons/Plus.vue'
import HeartOutlineIcon from 'vue-material-design-icons/HeartOutline.vue'
import CheckIcon from 'vue-material-design-icons/CheckBold.vue'
import FancyButton from './FancyButton.vue'

const { user, token, loadUser } = inject('auth')
const { createThemeFromColour } = inject('theme')

createThemeFromColour('#ff57b0')

const props = defineProps({
  gameId: {
    type: Number,
    required: true,
  },
  image: {
    type: String,
    required: true,
  },
  name: {
    type: String,
    required: true,
  },
  console: {
    type: String,
    required: true,
  },
  year: {
    type: Number,
    required: true,
  },
  price: {
    type: Number,
    required: true,
  },
  remove: {
    type: Function,
    required: true,
  },
})

const inCart = ref(false)

// check if a game with this id is in the user's cart or wishlist
const checkInCart = () => {
  if (user.value != null) {
    inCart.value = false
    user.value.cart.games.forEach((game) => {
      if (game.id == props.gameId) inCart.value = true
    })
  }
}

checkInCart()

/**
 * Add the game to the user's cart
 */
async function add() {
  const cartId = user.value.cart.id
  const requestOptions = {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json', Authorization: `Bearer ${token.value}` },
    body: JSON.stringify({ gameId: props.gameId }),
  }
  const response = await fetch(`http://localhost:8080/cart/${cartId}`, requestOptions)
  inCart.value = true
  loadUser()
  return response.json()
}

/**
 * Get the cover image for the game
 */
async function getCover() {
  const response = await fetch(`http://localhost:8080/games/${props.gameId}/cover`)
  return response.json()
}

const cover = ref(null)
cover.value = await getCover()
let coverStr = ''

// Build the cover image url string if the cover exists
if (cover.value && cover.value.type && cover.value.image) {
  coverStr = `data:image/${cover.value.type};base64,${cover.value.image}`
}
</script>

<template>
  <div class="wishlist-item">
    <hr />
    <div class="item">
      <img v-if="cover.type != null" class="game-cover" :src="coverStr" />
      <div class="game-info">
        <h2>{{ name }}</h2>
        <div class="game-details">
          <p class="game-console">{{ console }} •</p>
          <p class="game-year">{{ year }} •</p>
          <p class="game-price">${{ price }}</p>
        </div>
        <FancyButton
          filled
          :disabled="inCart"
          class="add"
          @click="add"
          :label="inCart ? 'Added to Cart' : 'Add to Cart'"
        >
          <CheckIcon v-if="inCart" />
          <PlusIcon v-else />
        </FancyButton>
        <FancyButton class="remove" @click="() => remove(gameId)" label="Remove">
          <HeartOutlineIcon />
        </FancyButton>
      </div>
    </div>
    <hr />
  </div>
</template>

<style scoped>
h2 {
  color: #ffffff;
  font-size: 32px;
}
button {
  background-color: #888888;
  color: #ffffff;
  border: none;
  border-radius: 100px;
  padding: 12px;
  margin: 16px 0px;
}
.add {
  background-color: #ff57b0;
  color: #ffffff;
  margin-right: 10px;
  display: inline-flex;
  align-items: center;
}
.remove {
  background-color: #ffd4eb;
  color: #ac3976;
  display: inline-flex;
  align-items: center;
}
.item {
  margin: 12px 20px;
}
.game-cover {
  max-width: 112px;
  max-height: 155px;
  display: inline-block;
  margin-right: 20px;
}
.game-info {
  display: inline-block;
  vertical-align: top;
}
.game-details {
  display: flex;
  gap: 4px;
  font-size: 20px;
}
.game-console {
  display: inline-block;
}
.game-year {
  display: inline-block;
}
.game-price {
  display: inline-block;
}
.icon {
  width: 16px;
  height: 16px;
  margin: 0px 10px;
}
</style>
