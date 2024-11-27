<!--
 Cart page
 @author Kennedy Olsen
-->
<script setup>
import { inject, ref, watch } from 'vue'
import CartItem from '@/components/CartItem.vue'

import zeldaCover from '@/assets/games/zelda.png'
import marioCover from '@/assets/games/mario.png'

<script setup>
import { inject } from 'vue'
import CartItem from '@/components/CartItem.vue'
const { createThemeFromColour } = inject('theme')
// change to a red theme to match the holiday effect
createThemeFromColour('#00ff00')
</script>

<template> 
  <main>
    <div class="title">
      <img class="icon" src="@/assets/icons/navbar/cart.png" />
      <h1>My Cart</h1>
    </div>
    <div class="cart-item">
      <CartItem />
    </div>
    <button class="checkout"> Checkout</button>
  </main>
  
</template>


<style scoped>
h1 {
  color: #ffffff;
  text-align: center;
  font-size: 36px;
}
button {
  background-color: #888888;
  color: #ffffff;
  border: none;
  border-radius: 100px;
  padding: 12px;
  margin: 16px 0px;
}
.title {
  display: flex;
  justify-content: center;
  align-items: center;
  column-gap: 14px;
}
.icon {
  width: 48px;
  height: 48px;
}
.cart-item {
  margin: 20px;
}
.checkout {
  background-color: #da8cb5;
  color: #ac3976;
}


</style>
