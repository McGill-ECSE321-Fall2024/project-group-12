<!--
 Cart page
 @author Kennedy Olsen
-->
<script setup>
import { inject, ref, watch } from 'vue'
import CartItem from '@/components/CartItem.vue'
import AnimatedLink from '@/components/AnimatedLink.vue'

const { createThemeFromColour } = inject('theme')
createThemeFromColour('#FF9797')
const { user, token } = inject('auth')

async function fetchData() {
  const cartId = user.value.cart.id
  const response = await fetch(`http://localhost:8080/cart/${cartId}`, {
    headers: {
      Authorization: `Bearer ${token.value}`,
    },
  })
  return response.json()
}

const data = ref(null)
data.value = await fetchData()

// update cart data when user updates
watch(user, async () => {
  data.value = await fetchData()
})

async function remove(gameId) {
  // TO FINISH ****
  alert('Removed from cart')
  const cartId = user.value.cart.Id
  const requestOpt = {
    method: 'put',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token.value}`,
     },
  }
  const response = await fetch(
    `http://localhost:8080/cart/${cartId}?remove=${gameId}`,
    requestOpt,
  )
  const cart = await response.json()
  data.value = cart
  return
}

const router = useRouter()

const goToCheckout = () => {
  router.push('/checkout')
}
</script>

<template>
  <main>
    <div class="title">
      <img class="icon" src="@/assets/icons/navbar/cart.png" />
      <h1>My Cart</h1>
    </div>
    <div class="cart-item">
      <!--to test-->
      <CartItem
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
    <AnimatedLink to="/checkout"><button class="checkout">Checkout</button></AnimatedLink>
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
