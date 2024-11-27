<!--
 Wishlist page
 @author Sophia Li
-->
<script setup>
import { inject, ref, watch } from 'vue'
import WishlistItem from '@/components/WishlistItem.vue'

const { user, token } = inject('auth')
const { createThemeFromColour } = inject('theme')
// change to a red theme to match the holiday effect
createThemeFromColour('#FF9797')

async function fetchData() {
  if (user.value == null) {
    // not signed in
    return { error: 'User not signed in' }
  }
  const wishlistId = user.value.wishlist.id
  const response = await fetch(`http://localhost:8080/wishlist/${wishlistId}`)
  return response.json()
}

const data = ref(null)
data.value = await fetchData()

async function fetchCart() {
  if (user.value == null) {
    // not signed in
    return { error: 'User not signed in' }
  }
  const cartId = user.value.cart.id
  const response = await fetch(`http://localhost:8080/cart/${cartId}`, {
    headers: {
      Authorization: `Bearer ${token.value}`,
    },
  })
  return response.json()
}

const cartdata = ref(null)
cartdata.value = await fetchCart()

// update wishlist and cart when the user updates
watch(user, async () => {
  data.value = await fetchData()
  cartdata.value = await fetchCart()
})

/**
 * Remove the game from the user's wishlist
 */
async function remove(gameId) {
  // TODO: fix remove to show the other games instead of a blank screen
  alert('Removed from wishlist')
  const wishlistId = user.value.wishlist.id
  const requestOptions = {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
  }
  const response = await fetch(
    `http://localhost:8080/wishlist/${wishlistId}?remove=${gameId}`,
    requestOptions,
  )
  const wishlist = await response.json()
  data.value = wishlist
  return
}
</script>

<template>
  <main>
    <div class="title">
      <img class="icon" src="@/assets/icons/navbar/wishlist.png" />
      <h1>My Wishlist</h1>
    </div>
    <div class="placeholder" v-if="user == null">Sign in to add games to your wishlist!</div>
    <div class="placeholder" v-else-if="data.error">
      An error occurred when retreiving your wishlist. Please try again later.
    </div>
    <div class="placeholder" v-else-if="data.games?.length == 0">Your wishlist is empty.</div>
    <div v-else class="wishlist-items">
      <p>{{ data }}</p>
      <!-- for testing purposes -->
      <p>{{ cartdata }}</p>
      <!-- for testing purposes -->
      <WishlistItem
        v-for="item in data.games"
        :key="item.id"
        :gameId="item.id"
        :image="item.image"
        :name="item.name"
        :console="item.console"
        :year="item.year"
        :price="item.price"
        :remove="remove"
      />
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
  color: #ffffff;
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
