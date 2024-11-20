<script setup>
import { useTemplateRef } from 'vue'
import AnimatedLink from './AnimatedLink.vue'

const gameCover = useTemplateRef('gameCover')

defineProps({
  name: {
    type: String,
    required: true,
  },
  img: {
    type: String,
    required: true,
  },
  id: {
    type: Number,
    required: true
  }
})

function prepareAnimation() {
  gameCover.value.style.viewTransitionName = 'game-cover';
}

</script>

<template>
  <AnimatedLink @click="prepareAnimation" :to="'/game/' + id">
    <div class="game-card">
      <img :src="img" ref="gameCover" />
      <div class="game-label--container">
        <p class="body">{{ name }}</p>
      </div>
    </div>
  </AnimatedLink>
</template>

<style scoped>
.game-card {
  width: 144px;
  margin: 0 16px;
  display: inline-block;
  vertical-align: top;
}
.game-card img {
  width: 144px;
  height: 200px;
  object-fit: cover;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.25);
  transition: transform 0.2s;
}
.game-card p {
  color: white;
  line-height: 16px;
  text-align: center;
  text-wrap: pretty;
}
.game-label--container {
  width: 100%;
  height: 32px;
  align-content: center;
}
/* hover style for the game card. set the view transition name so it animates */
.game-card:hover img {
  transform: translateY(-2px);
}
</style>
