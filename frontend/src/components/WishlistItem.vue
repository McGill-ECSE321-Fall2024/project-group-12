<script setup></script>

<template>
  <div class="wishlist-item">
    <hr />
    <div class="item">
      <img class="game-cover" src="@/assets/games/minecraft.png" />
      <div class="game-info">
        <h2>Minecraft</h2>
        <div class="game-details">
          <p class="game-console">PC</p>
          <img class="icon" src="@/assets/icons/navbar/wishlist.png" />
          <p class="game-year">2011</p>
          <img class="icon" src="@/assets/icons/navbar/wishlist.png" />
          <p class="game-price">$29.99</p>
        </div>
        <button class="add">+ Add to Cart</button>
        <button class="remove">- Remove from Wishlist</button>
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
  height: 155px;
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
