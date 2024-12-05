<!--
 For cart page
 @author Kennedy Olsen
-->
<script setup>
import { inject, ref } from 'vue'
const { user, token } = inject('auth')

const props = defineProps({
  gameId: {
    type: Number,
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
  remove: {
    type: Function,
    required: true,
  },
})

const coverImg = ref('')
// load cover image
async function loadCover() {
  const response = await fetch(`http://localhost:8080/games/${props.gameId}/cover`)
  if (response.ok) {
    console.log('loaded image')
    const { image, type } = await response.json()

    coverImg.value = `data:image/${type};base64,${image}`
  }
}

loadCover()

async function removeGame(gameId) {
  // TO FINISH ****
  alert('Removed from cart')
  const cartId = user.value.cart.id
  console.log(cartId)
  const requestOpt = {
    method: 'put',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token.value}`,
    },
  }
  const response = await fetch(`http://localhost:8080/cart/${cartId}?remove=${gameId}`, requestOpt)
  await response.json()
  location.reload()
  return
}
</script>

<template>
  <div class="cart-item">
    <hr />
    <!--creates horizontal line-->
    <div class="item">
      <img v-if="coverImg != ``" class="game-cover" :src="coverImg" />
      <div class="game-info">
        <h2>{{ name }}</h2>
        <!--game title-->
        <div class="game-details">
          <p class="game-console">{{ console }}</p>
          <!--PC/XBOX/nintendo etc-->

          <p class="game-year">{{ year }}</p>
          <!--year of release-->
          <p class="game-price">${{ price }}</p>
          <!--price-->
        </div>
        <button
          class="remove"
          @click="
            () => {
              removeGame(gameId)
            }
          "
        >
          Remove from Cart
        </button>
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
  background-color: #fda3ce;
  color: #b22279;
  border: none;
  border-radius: 100px;
  padding: 12px;
  margin: 16px 0px;
}
.remove {
  background-color: #eac0d6;
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
  aspect-ratio: 144 / 200;
  object-fit: cover;
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
  margin-right: 9px;
}
.game-year {
  display: inline-block;
  margin-right: 9px;
}
.game-price {
  display: inline-block;
}
</style>
