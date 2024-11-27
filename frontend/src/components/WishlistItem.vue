<script setup>
import { inject, ref } from 'vue'
import PlusIcon from 'vue-material-design-icons/Plus.vue'
import HeartOutlineIcon from 'vue-material-design-icons/HeartOutline.vue'

const { user, token } = inject('auth')

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

/**
 * Add the game to the user's cart
 */
async function add() {
  alert('Added to cart')
  const cartId = user.value.cart.id
  const requestOptions = {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json', Authorization: `Bearer ${token.value}` },
    body: JSON.stringify({ gameId: props.gameId }),
  }
  const response = await fetch(`http://localhost:8080/cart/${cartId}`, requestOptions)
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
          <p class="game-console">{{ console }}</p>
          <img class="icon" src="@/assets/icons/navbar/wishlist.png" />
          <p class="game-year">{{ year }}</p>
          <img class="icon" src="@/assets/icons/navbar/wishlist.png" />
          <p class="game-price">${{ price }}</p>
        </div>
        <button class="add" @click="add"><PlusIcon /> Add to Cart</button>
        <button class="remove" @click="() => remove(gameId)">
          <HeartOutlineIcon /> Remove from Wishlist
        </button>
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
