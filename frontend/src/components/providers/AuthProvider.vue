<script setup>
import { ref, provide } from 'vue'

// check if a token is stored in localStorage
let authResponse = JSON.parse(localStorage.getItem('auth'))
let token = ref(authResponse?.token)

// store the user, once loaded
const user = ref(null)
let loadedToken = token.value

const loadUser = async () => {
  console.log('attempting to load user')
  console.log(token.value, loadedToken)
  // this method will attempt to load user data from the backend.
  // if the user's already been loaded, return right away
  if (user.value != null && token.value == loadedToken) {
    console.log('returning stored user')
    return
  }
  // if there's no token, there's no user to be found so return null
  if (token.value == null) {
    console.log('returning null')
    user.value = null
    loadedToken = null
    return
  }

  // otherwise, make the call to load the user data
  authResponse = JSON.parse(localStorage.getItem('auth'))
  const { userType, id } = authResponse
  const resp = await fetch(`http://localhost:8080/${userType.toLowerCase()}s/${id}`, {
    headers: {
      Authorization: `Bearer ${token.value}`,
    },
  })
  user.value = await resp.json()
  loadedToken = token.value
  console.log('updated user')
  return
}

// load the user initially
loadUser()

// give sign in, sign up, and sign out methods
const signIn = async (email, password) => {
  // POST to the auth endpoint
  const resp = await fetch('http://localhost:8080/auth/signin', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ email, password }),
  })
  // read the response
  const data = await resp.json()
  const newToken = data.token
  const id = data.id
  const userType = data.userType
  // store the data
  localStorage.setItem(
    'auth',
    JSON.stringify({
      token: newToken,
      id,
      userType,
    }),
  )
  // update the token
  token.value = newToken
  // load the user again
  loadUser()
}

const signUp = async (name, email, phoneNumber, password) => {
  // POST with the form data to
  const resp = await fetch('http://localhost:8080/customers', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      name,
      email,
      phoneNumber,
      password,
    }),
  })
  // read the response
  const data = await resp.json()
  const newToken = data.token
  const id = data.id
  // store the data
  localStorage.setItem(
    'auth',
    JSON.stringify({
      token: newToken,
      id,
      userType: 'CUSTOMER',
    }),
  )
  // update the token
  token.value = newToken
  // load the user again
  loadUser()
}

const signOut = () => {
  localStorage.removeItem('auth')
  token.value = null
  // load the user again
  loadUser()
}

// give components access to the values and methods
provide('auth', {
  token,
  user,
  loadUser,
  signIn,
  signUp,
  signOut,
})
</script>

<!-- no content, this component only exists to manage user auth state -->
<template>
  <slot />
</template>
