<script setup>
import store from '@/store'
</script>

<template>
  <button @click="store.count++">Count: {{ store.count }}</button>
</template>

<style scoped>
button {
  background: var(--theme-primary);
}
</style>
