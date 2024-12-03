<script setup>
import { defineProps } from 'vue'
const props = defineProps({
  order: {
    type: Object,
    required: true,
    default: () => ({
      id: null,
      games: [],
    }),
  },
})
</script>

<template>
  <div class="order-card">
    <div class="heading">
      <div>
        <h2>Order delivered November 20, 2022</h2>
        <h3 class="total" :style="{ 'font=size': '1rem' }">Total: $100</h3>
      </div>
      <h3>Order Number: #1000</h3>
    </div>

    <div class="game" v-for="game in order.games" :key="game.id">
      <img
        src="../assets/games/minecraft.png"
        class="game-img"
        :style="{ 'grid-row': 'index + 2' }"
      />
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
          <button :style="{ background: 'rgba(65, 93, 67, 1)' }">Leave a review</button>
          <button :style="{ background: 'rgba(162, 62, 72, 1)' }">Return item</button>
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
}

.heading {
  grid-area: 1 / 1 / 2 / 5;
  display: flex;
  justify-content: space-between;
  font-size: 1.5rem;
}

.game {
  padding-bottom: 3rem;
}
.game-img {
  width: 100%;
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
