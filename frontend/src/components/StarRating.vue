<!--
  component for displaying a star rating
  @author James Madden
-->
<script setup>
import StarIcon from 'vue-material-design-icons/Star.vue'
import StarOutlineIcon from 'vue-material-design-icons/StarOutline.vue'

defineProps({
  rating: Number,
})
</script>

<template>
  <span class="game-info--review-stars">
    <StarIcon v-for="n in rating" v-bind:key="n" fillColor="#dbb700"></StarIcon>
    <StarOutlineIcon
      v-for="n in 5 - rating"
      v-bind:key="rating + n"
      fillColor="#dbb700"
    ></StarOutlineIcon>
  </span>
</template>
