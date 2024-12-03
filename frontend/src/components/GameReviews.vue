<!-- calls the API to load reviews for a specific game,
 plus if there's a user signed in, provides a form for leaving your review
-->

<script setup>
import { ref, inject } from 'vue'
import ReviewCard from '@/components/ReviewCard.vue'

const props = defineProps({
  id: {
    type: Number,
    required: true,
  }
})

// use user auth state to detect whether a review can be placed
const { user, token } = inject('auth')

const loadReviews = () => {
  return new Promise(resolve => {
    setTimeout(() => {
      resolve(true)
    }, 4000)
  })
}

// load the reviews
const reviews = ref(await loadReviews())
</script>

<template>
  <form class="reviews-form" v-if="user != null">
    <h2>Leave a Review</h2>
  </form>
  <div class="reviews-form" v-else>
    <h2>You must be signed in to place a review.</h2>
  </div>
  <div class="reviews-grid">

    <ReviewCard :rating="5" review="Best game ever!" :id="1"></ReviewCard>
    <ReviewCard :rating="3" review="This game is okay" :id="1"></ReviewCard>
    <ReviewCard :rating="1" review="I hate this stupid game" :id="1"></ReviewCard>
    <ReviewCard :rating="0" review="this game SUCKS" :id="1"></ReviewCard>
    <ReviewCard :rating="4" review="Incredible job nintendo" :id="1"></ReviewCard>

  </div>
</template>

<style scoped>
.reviews-grid {
  width: 100%;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  grid-gap: 16px;
}
</style>
