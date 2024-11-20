<script setup>
import store from '@/store'
</script>

<template>
  <button @click="store.count++">Count: {{ store.count }}</button>
</template>
