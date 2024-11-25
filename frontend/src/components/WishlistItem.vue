<script setup>
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
})

/**
 * Add the game to the user's cart
 *
 * TODO: test the request/response
 */
async function add() {
  alert('Added to cart')
  const cartId = 4952; // change this to the user's cart ID, which should just be the customer ID
  const requestOptions = {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ gameId: props.gameId }),
  };
  const response = await fetch(`http://localhost:8080/cart/${cartId}`, requestOptions);
  return response.json();
}

/**
 * Remove the game from the user's wishlist
 * 
 * TODO: refresh state after removing
 */
async function remove() {
  alert('Removed from wishlist')
  const wishlistId = 4952; // change this to the user's wishlist ID
  const requestOptions = {
    method: "PUT",
    headers: { "Content-Type": "application/json" }
  };
  const response = await fetch(`http://localhost:8080/wishlist/${wishlistId}?remove=${props.gameId}`, requestOptions);
  return response.json();
}
</script>

<template>
  <div class="wishlist-item">
    <hr />
    <div class="item">
      <img class="game-cover" :src="image" />
      <div class="game-info">
        <h2>{{name}}</h2>
        <div class="game-details">
          <p class="game-console">{{console}}</p>
          <img class="icon" src="@/assets/icons/navbar/wishlist.png" />
          <p class="game-year">{{year}}</p>
          <img class="icon" src="@/assets/icons/navbar/wishlist.png" />
          <p class="game-price">${{price}}</p>
        </div>
        <button class="add" @click="add">+ Add to Cart</button>
        <button class="remove" @click="remove">- Remove from Wishlist</button>
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
}
.remove {
  background-color: #ffd4eb;
  color: #ac3976;
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
