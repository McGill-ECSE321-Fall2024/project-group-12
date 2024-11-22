<!--
 Wishlist page
 @author Sophia Li
-->
<script setup>
import { inject } from 'vue'
import WishlistItem from '@/components/WishlistItem.vue'
const { createThemeFromColour } = inject('theme')
// change to a red theme to match the holiday effect
createThemeFromColour('#FF9797')
</script>

<template> 
  <main>
    <div class="title">
      <img class="icon" src="@/assets/icons/navbar/wishlist.png" />
      <h1>My Wishlist</h1>
    </div>
    <div class="wishlist-items">
      <WishlistItem />
    </div>
  </main>
</template>

<style scoped>
h1 {
  color: #ffffff;
  text-align: center;
  font-size: 36px;
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
