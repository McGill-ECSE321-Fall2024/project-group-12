<script setup>
import { inject } from 'vue'
import SigninView from '@/views/SigninView.vue'
// load the current user
const { user, signOut } = inject('auth')

console.log('user view loaded')
</script>

<template>
  <!-- login page can go here on mobile, on desktop it'll be a popup -->
  <!-- IF current user is null -->
  <SigninView v-if="user == null" />
  <!-- otherwise, the normal page can be shown -->
  <div v-else class="user">
    <img src="@/assets/logo.svg" />
    <h1>User {{ user.name }} signed in</h1>
    <button @click="signOut">Sign out</button>
  </div>
</template>

<style scoped>
img {
  position: absolute;
  bottom: 16px;
  right: 16px;
  width: 64px;
  view-transition-name: logo;
}
</style>
