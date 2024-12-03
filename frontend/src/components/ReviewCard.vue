<!-- displays an individual review and its comments -->
<script setup>
import FancyButton from '@/components/FancyButton.vue'
import CommentIcon from 'vue-material-design-icons/Comment.vue'
import StarRating from '@/components/StarRating.vue'

defineProps({
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
  }
})
</script>

<template>
  <div class="review-card">
    <div class="review-stars">
      <StarRating :rating="rating"></StarRating>
    </div>
    <h2>{{ review }}</h2>
    <FancyButton small label="Comments">
      <CommentIcon size="14"></CommentIcon>
    </FancyButton>
  </div>
</template>

<style scoped>
.review-card {
  box-sizing: border-box;
  width: 100%;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 8px;
  padding: 8px;
  /* make button grey */
  --theme-primary: #333;
  --theme-primary-container: #e3e3e3;
}
</style>
