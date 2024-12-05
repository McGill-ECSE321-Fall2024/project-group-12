<script setup>
import store from '@/store.js'
</script>

<template>
  <div class="search-bar">
    <img src="@/assets/icons/navbar/search.png" />
    <input class="search-input" type="search" placeholder="Search..." v-model="store.searchQuery" />
  </div>
</template>

<style scoped>
.search-bar {
  position: relative;
  width: 100%;
  height: 48px;
  background: #111;
  border-radius: 24px;
  display: flex;
  flex-direction: row;
}
.search-bar img {
  height: 32px;
  padding: 0 8px;
  position: absolute;
  margin: 8px;
  pointer-events: none;
}
.search-input {
  width: 100%;
  height: 100%;
  padding-left: 56px;
  background: transparent;
  color: white;
  font-size: 20px;
  font-weight: 300;
  border: none;
  outline: none;
}
</style>
