<script setup>
import { ref, provide } from 'vue'

// check if a token is stored in localStorage
let authResponse = JSON.parse(localStorage.getItem('auth'))
let token = ref(authResponse?.token)

// store the user, once loaded
const user = ref(null)
let loadedToken = token.value

let promise = null
const loadUser = async () => {
  console.log('loading user...')
  // this method will attempt to load user data from the backend.
  // if the user's already been loaded, return right away
  if (user.value != null && token.value == loadedToken) return user
  console.log('no user value already')
  loadedToken = token.value
  // if there's no token, there's no user to be found so return null
  if (token.value == null) {
    user.value == null
    return null
  }
  console.log('token not null')
  // if the loading has already started but hasn't completed, return that promise instead of calling again
  if (promise != null) return promise
  console.log('no pending promise')

  // otherwise, make the call to load the user data
  authResponse = JSON.parse(localStorage.getItem('auth'))
  const { userType, id } = authResponse
  const resp = await fetch(`http://localhost:8080/${userType.toLowerCase()}s/${id}`, {
    headers: {
      Authorization: `Bearer ${token.value}`,
    },
  })
  user.value = await resp.json()
  promise = null
  return user.value
}

// load the user initially
promise = loadUser()

// give sign in, sign up, and sign out methods
const signOut = () => {
  localStorage.removeItem('auth')
  token.value = null
}

// give components access to the values and methods
provide('auth', {
  token,
  loadUser,
  signOut,
})
</script>

<!-- no content, this component only exists to manage user auth state -->
<template>
  <slot />
</template>
