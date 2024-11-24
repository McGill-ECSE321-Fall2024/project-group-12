<!--
 Wishlist page
 @author Sophia Li
-->
<script setup>
import { inject, ref } from 'vue'
import WishlistItem from '@/components/WishlistItem.vue'

import minecraftCover from '@/assets/games/minecraft.png'
import zeldaCover from '@/assets/games/zelda.png'
import marioPartyJamboreeCover from '@/assets/games/mario_party_jamboree.png'

const { user } = inject('auth')
const { createThemeFromColour } = inject('theme')
// change to a red theme to match the holiday effect
createThemeFromColour('#FF9797')

async function fetchData() {
  const wishlistId = 0;
  const response = await fetch(`http://localhost:8080/wishlist/${wishlistId}`);
  return await response.json();
}
const data = ref(null);
data.value = await fetchData();
</script>

<template>
  <main>
    <div class="title">
      <img class="icon" src="@/assets/icons/navbar/wishlist.png" />
      <h1>My Wishlist</h1>
    </div>
    <div class="placeholder" v-if="user == null" >Sign in to add games to your wishlist!</div>
    <div v-else class="wishlist-items">
      <p>{{ user }}</p>
      <WishlistItem :image="zeldaCover" name="The Legend of Zelda: Tears of the Kingdom" console="Nintendo" :year="2023" :price="79.99" />
      <WishlistItem :image="minecraftCover" name="Minecraft" console="PC" :year="2011" :price="29.99" />
      <WishlistItem :image="marioPartyJamboreeCover" name="Minecraft" console="PC" :year="2011" :price="29.99" />
    </div>
  </main>
</template>

<style scoped>
h1 {
  color: #ffffff;
  text-align: center;
  font-size: 36px;
}
.placeholder {
  padding: 20px;
  text-align: center;
  color:#ffffff;
  font-style: italic;
}
.title {
  display: flex;
  justify-content: center;
  align-items: center;
  column-gap: 14px;
  /* margin-top: 20px;
    margin-bottom: 20px; */
}
.icon {
  width: 48px;
  height: 48px;
}
.wishlist-items {
  margin: 20px;
}
</style>
