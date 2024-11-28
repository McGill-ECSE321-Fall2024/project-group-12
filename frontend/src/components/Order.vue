<script setup>
// :category="order.category"
//           :console="order.console"
//           :inventory="order.inventory"
//           :price="order.price"
//           :name="order.name"
//           :description="order.description"
//           :status="order.status"
//           :year="order.year"
// Define props interface
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
        <h3 class="total" :style="{ 'font=size': '1rem;' }">Total: $100</h3>
      </div>
      <h3>Order Number: #1000</h3>
    </div>

    <div v-for="game in order.games" :key="game.id">
      <img
        src="../assets/games/minecraft.png"
        class="game-img"
        :style="{ 'grid-row': 'index + 2' }"
      />
      <div class="game" :style="{ 'grid-row': 'index + 2' }">
        <h3 class="game-title" :style="{ 'font=size': '1rem;' }">{{ game.name }}</h3>
        <div class="game-details">
          <h4>{{ game.console }}</h4>
          •
          <h4>{{ game.year }}</h4>
          •
          <h4>${{ game.price }}</h4>
          •
          <h4>Rating</h4>
        </div>
        <div class="buttons" :style="{ 'grid-row': 'index + 2' }">
          <button class="review-button">Leave a review</button>
          <button class="return-button">Return item</button>
        </div>
      </div>
    </div>

    <!-- <div class="buttons" :style="{ 'grid-row': 'index + 2' }">
            <button v-if="review.user==user">Edit your review</button>
            <button class="review-button">Leave a review</button>
            <button class="return-button">Return item</button>
        </div> -->
  </div>
</template>

<style scoped>
.order-card {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-auto-rows: minmax(150px, auto);
  grid-column-gap: 7rem;
  background-color: #111111;
  border-radius: 40px;
  padding: 2rem;
}

.heading {
  grid-area: 1 / 1 / 2 / 4;
  display: flex;
  justify-content: space-between;
  font-size: 1.5rem;
}

.game-img {
  grid-column: 1;
  width: 100%;
}

.game {
  grid-column: 2;
}
.game-details {
  display: flex;
  gap: 0.5rem;
}

.buttons {
  grid-column: 3;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
}

button {
  background: inherit;
  color: #ffffff;
  border: none;
  border-radius: 100px;
  padding: 0.75rem;
  margin-top: 0.5rem;
}

.review-button {
  background: rgba(65, 93, 67, 1);
}
.return-button {
  background: rgba(162, 62, 72, 1);
}
</style>
