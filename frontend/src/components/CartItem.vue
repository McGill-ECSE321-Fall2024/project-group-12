<!--
 For cart page
 @author Kennedy Olsen
-->

<script setup></script>

<template>
    <div class="cart-item">
      <hr /> <!--creates horizontal line-->
      <div class="item">
        <img class="game-cover" src="@/assets/games/minecraft.png" />
        <div class="game-info">
          <h2>Minecraft</h2> <!--game title-->
          <div class="game-details">
            <p class="game-console">PC</p> <!--PC/XBOX/nintendo etc-->
            
            <p class="game-year">2011</p> <!--year of release-->
            <p class="game-price">$29.99</p> <!--price-->
          </div>
          <button class="remove">- Remove from Cart</button>
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
  margin-right: 10px;
}
.game-year {
  display: inline-block;
  margin: 10px;
}
.game-price 
  display: inline-block;
  margin: 10px;
}
.icon {
  width: 16px;
  height: 16px;
  margin: 0px 10px;
}

</style>
