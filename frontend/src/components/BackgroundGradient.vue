<script setup>
defineProps({
  colour: {
    type: String,
    required: true,
  },
})
</script>

<template>
  <div class="gradient"></div>
</template>

<style scoped>
.gradient {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 320px;
  pointer-events: none;
  background: linear-gradient(rgba(v-bind(colour), 0.1), rgba(v-bind(colour), 0));
}
</style>
