<!--
 Order card page
 @author Amy Ding
-->
<script setup>
import { ref } from 'vue'
import AnimatedLink from './AnimatedLink.vue'

const props = defineProps({
  order: {
    type: Object,
    // required: true,
    default: () => ({
      id: null,
      deliveryAddress: '',
      games: [],
      purchaseDate: '',
      purchaseTotal: 0,
      status: '',
    }),
  },
})

const formatDate = (dateString) => {
  const options = {
    weekday: 'long',
    year: 'numeric',
    month: 'long',
    day: 'numeric',
  }
  return new Date(dateString).toLocaleString(options)
}

// the data URL for each game's cover
const gameCovers = ref([])
// call the server for each game to get its cover
const loadGameCovers = async () => {
  // create a fetch for each game
  const responses = await Promise.all(
    props.order.games.map((game) => fetch(`http://localhost:8080/games/${game.id}/cover`)),
  )

  // go through each response to get the cover image
  const json = await Promise.all(responses.map((response) => response.json()))

  gameCovers.value = json.map(({ image, type }) => `data:image/${type};base64,${image}`)
}
loadGameCovers()
async function returnOrder(event) {
  event.preventDefault()
  const authResponse = JSON.parse(localStorage.getItem('auth'))
  // check whether auth response exists
  if (!authResponse) return []
  const { token } = authResponse
  const resp = await fetch(`http://localhost:8080/orders/${props.order.id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
      Accept: 'application/json',
    },
    body: JSON.stringify({
      status: 'Returned',
    }),
  })
  if (resp.errors) {
    alert(resp.errors)
  } else {
    alert('Order successfully returned!')
    location.reload()
  }
}
const goToGame = (gameId) => {
  // for review button functionality
  router.push({
    name: 'game',
    params: { id: gameId },
  })
}
</script>

<template>
  <div class="order-card">
    <div class="heading">
      <div>
        <div v-if="order.status === 'Returned'">
          <h2 class="returned-heading">Order {{ formatDate(order.purchaseDate) }}</h2>
          <h2 class="returned">RETURNED</h2>
        </div>
        <h2 v-else>Order {{ order.status }} {{ formatDate(order.purchaseDate) }}</h2>
        <h3 class="total" :style="{ 'font=size': '1rem' }">Total: ${{ order.purchaseTotal }}</h3>
      </div>
      <div class="subheading">
        <h3>Order Number: #{{ order.id }}</h3>
        <button class="return-button" :disabled="order.status === 'Returned'" @click="returnOrder">
          Return order
        </button>
      </div>
    </div>

    <div class="game" v-for="(game, index) in order.games" :key="game.id">
      <img :src="gameCovers[index]" class="game-img" />
      <div>
        <h3 class="game-title" :style="{ 'font=size': '1rem' }">{{ game.name }}</h3>
        <div class="game-details">
          <h4>{{ game.console }}</h4>
          •
          <h4>{{ game.year }}</h4>
          •
          <h4>${{ game.price }}</h4>
          •
          <h4>Rating</h4>
        </div>
        <div class="buttons">
          <AnimatedLink :to="`/game/${game.id}`"
            ><button :style="{ background: 'rgba(65, 93, 67, 1)' }">
              Leave a review
            </button></AnimatedLink
          >
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.order-card {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;
  grid-auto-rows: minmax(150px, auto);
  grid-column-gap: 5rem;
  background-color: #111111;
  border-radius: 40px;
  padding: 2rem;
  margin-bottom: 2rem;
}
.heading {
  grid-area: 1 / 1 / 2 / 5;
  display: flex;
  justify-content: space-between;
  font-size: 1.5rem;
  padding-bottom: 2rem;
}
.subheading {
  display: flex;
  flex-direction: column;
}
.game {
  padding-bottom: 3rem;
}
.game-img {
  width: 100%;
  aspect-ratio: 144 / 200;
  object-fit: cover;
}
.game-details {
  display: flex;
  gap: 0.5rem;
  font-size: 0.7rem;
}
.buttons {
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
}
button {
  color: #ffffff;
  border: none;
  border-radius: 100px;
  padding: 0.75rem;
  margin-top: 0.5rem;
}
.return-button {
  background: rgba(162, 62, 72, 1);
}
.return-button:disabled {
  display: none;
}
.returned-heading {
  text-decoration: line-through;
}
.returned {
  color: rgba(162, 62, 72, 1);
  text-decoration: none currentColor solid auto;
}

@media only screen and (max-width: 600px) {
  .order-card {
    grid-template-columns: 1fr;
  }
  .heading {
    grid-area: 1 / 1 / 2 / 2;
    display: flex;
    flex-direction: column;
  }
}
</style>
