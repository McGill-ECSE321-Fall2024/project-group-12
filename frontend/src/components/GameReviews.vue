<!-- calls the API to load reviews for a specific game,
 plus if there's a user signed in, provides a form for leaving your review
-->

<script setup>
import { ref, inject } from 'vue'
import ReviewCard from '@/components/ReviewCard.vue'
import FancyInput from '@/components/FancyInput.vue'
import FancyButton from '@/components/FancyButton.vue'

const props = defineProps({
  id: {
    type: Number,
    required: true,
  }
})

// use user auth state to detect whether a review can be placed
const { user, token } = inject('auth')

// keep track of whether the current user has posted a review
const hasPosted = ref(false)

const loadReviews = async () => {
  // fetch the reviews
  const response = await fetch(`http://localhost:8080/games/${props.id}/reviews`)
  const reviewData = await response.json()

  // go through and see if any review has this user's ID
  for (let review of reviewData) {
    if (review.customerId == user.value.id) hasPosted.value = true
  }

  return reviewData
}

const postReview = async event => {
  // stop the form from refreshing the page
  event.preventDefault();

  // get the values from the inputs
  const comment = event.target.querySelector('#comment').value
  const score = event.target.querySelector('#score').value

  // hide the form
  hasPosted.value = true

  // then submit the POST
  const response = await fetch(`http://localhost:8080/reviews`, {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token.value}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      review: comment,
      rating: score,
      gameId: props.id,
      customerId: user.value.id,
      likeCount: 0
    })
  })
  if (response.ok) {
    const newReview = await response.json()
    // add the new review to the list to render
    reviews.value.push(newReview)
  } else {
    hasPosted.value = false
  }
}
// load the reviews
const reviews = ref(await loadReviews())
</script>

<template>
  <form class="reviews-form" v-if="user != null && !hasPosted" @submit="postReview">
    <h2>Leave a Review</h2>
    <div class="reviews-form-row">
      <FancyInput name="comment" label="Review" />
      <select name="score" id="score">
        <option :value="1">1 star</option>
        <option :value="2">2 stars</option>
        <option :value="3">3 stars</option>
        <option :value="4">4 stars</option>
        <option :value="5">5 stars</option>
      </select>
      <FancyButton label="Post" small />
    </div>
  </form>
  <div class="reviews-form" v-else-if="user == null">
    <h2>You must be signed in to place a review.</h2>
  </div>
  <div class="reviews-grid" v-if="reviews.length > 0">

    <ReviewCard v-for="review in reviews" :rating="review.rating" :review="review.review" :id="review.id" v-bind:key="review.id"></ReviewCard>

  </div>
  <h2 v-else>This game has no reviews.</h2>
</template>

<style scoped>
.reviews-form-row {
  display: flex;
  justify-content: center;
  align-items: center;
}
.reviews-grid {
  width: 100%;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  grid-gap: 16px;
}
</style>
