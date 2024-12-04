<!-- displays an individual review and its comments -->
<script setup>
import { inject, ref } from 'vue'
import FancyInput from '@/components/FancyInput.vue'
import FancyButton from '@/components/FancyButton.vue'
import CommentIcon from 'vue-material-design-icons/Comment.vue'
import StarRating from '@/components/StarRating.vue'

const props = defineProps({
  rating: {
    type: Number,
    required: true,
  },
  review: {
    type: String,
    required: true,
  },
  id: {
    type: Number,
    required: true
  },
  customerId: {
    type: Number,
    required: true
  },
  gameId: {
    type: Number,
    required: true
  }
})

const { user, token } = inject('auth')
// allow the review to be edited if this user made it
const editMode = ref(false)
// turn props into refs so they can be updated if the review is updated
const rating = ref(props.rating)
const review = ref(props.review)

const updateReview = async () => {

  // get the new values
  const newReview = document.querySelector('#comment').value
  const newRating = new Number(document.querySelector('#score').value)

  // if comment is empty, can't update - show error msg
  if (newReview == '') {
    return
  }

  // make a put request
  const response = await fetch(`http://localhost:8080/reviews/${props.id}`, {
    method: 'PUT',
    headers: {
      'Authorization': `Bearer ${token.value}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      likeCount: 0,
      review: newReview,
      rating: newRating,
      gameId: props.gameId,
      customerId: props.customerId,
    })
  })
  if (response.ok) {
    editMode.value = false
    // update the local values
    rating.value = newRating + 0
    review.value = newReview
  }

}

</script>

<template>
  <div class="review-card">
    <div class="review-stars">
      <select v-if="editMode" name="score" id="score" :value="rating">
        <option :value="1">1 star</option>
        <option :value="2">2 stars</option>
        <option :value="3">3 stars</option>
        <option :value="4">4 stars</option>
        <option :value="5">5 stars</option>
      </select>
      <StarRating v-else :rating="rating"></StarRating>
    </div>

    <FancyInput v-if="editMode" :value="review" name="comment" label="Review"></FancyInput>
    <h2 v-else>{{ review }}</h2>

    <div class="review--button-row">
      <FancyButton small label="Comments">
        <CommentIcon size="14"></CommentIcon>
      </FancyButton>
      <!-- show an edit button if this comment belongs to the current user -->
      <FancyButton v-if="customerId == user.id && !editMode" small label="Edit" @click="() => {editMode = true}"></FancyButton>
      <FancyButton v-else-if="customerId == user.id && editMode" small label="Save" @click="updateReview"></FancyButton>
    </div>
  </div>
</template>

<style scoped>
.review-card {
  box-sizing: border-box;
  width: 100%;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 8px;
  padding: 8px;
}
.review--button-row {
  display: flex;
  flex-direction: row;
  gap: 8px;
  /* make buttons grey */
  --theme-primary: #333;
  --theme-primary-container: #e3e3e3;
}
</style>
