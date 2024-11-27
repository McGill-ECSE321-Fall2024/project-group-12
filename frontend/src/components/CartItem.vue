<!--
 For cart page
 @author Kennedy Olsen
-->
<script setup>
import { inject } from 'vue'
const { user, token } = inject('auth')

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
  remove: {
    type: Function,
    required: true,
  },
})

/**
 * Get the cover image for the game
 */
 async function getCover() {
  const response = await fetch(`http://localhost:8080/games/${props.gameId}/cover`)
  return response.json()
}
const cover = ref(null)
cover.value = await getCover()
let coverStr = ''

// Build the cover image url string if the cover exists
if (cover.value && cover.value.type && cover.value.image) {
  coverStr = `data:image/${cover.value.type};base64,${cover.value.image}`
}

</script>



<template>
  <div class="cart-item">
    <hr /> <!--creates horizontal line-->
    <div class="item">
      <img v-if="cover.type != null" class="game-cover" :src="coverStr" />
      <div class="game-info">
        <h2>{{ name }}</h2> <!--game title-->
        <div class="game-details">
          <p class="game-console">{{ console }}</p> <!--PC/XBOX/nintendo etc-->
          
          <p class="game-year">{{ year }}</p> <!--year of release-->
          <p class="game-price">${{ price }}</p> <!--price-->
        </div>
        <button class="remove" @click="() => removeItem(gameId)"> Remove from Cart</button>
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
</style>
