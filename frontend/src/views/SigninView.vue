<!-- either acts as the user page on mobile, or the contents of a popup on desktop -->
<script setup>
import { inject, ref } from 'vue'

// allow toggle between sign in and sign up
const showingSignIn = ref(true) // if false, shows sign up

// get the ref for token so it can be updated
const { signIn, signUp } = inject('auth')

const toggleShowingSignIn = () => (showingSignIn.value = !showingSignIn.value)

const submitSignIn = async (event) => {
  event.preventDefault()

  const form = event.target
  // load the data from each input
  const email = form.querySelector('#email').value
  const password = form.querySelector('#password').value

  signIn(email, password)

}

const submitSignUp = async (event) => {
  event.preventDefault()

  const form = event.target
  // load the data from each input
  const name = form.querySelector('#name').value
  const email = form.querySelector('#email').value
  const phoneNumber = form.querySelector('#phone-number').value
  const password = form.querySelector('#password').value

  signUp(name, email, phoneNumber, password)

}
</script>

<template>
  <div v-if="showingSignIn" class="signin-container">
    <h1>Sign In</h1>
    <p>Don't have an account? <a @click="toggleShowingSignIn">Sign up</a></p>

    <form @submit="submitSignIn">
      <label for="email">Email</label>
      <input type="email" id="email" />
      <br />

      <label for="password">Password</label>
      <input type="password" id="password" />
      <br />

      <button>Sign In</button>
    </form>
  </div>
  <div v-else class="signup-container">
    <h1>Sign Up</h1>
    <p>Already have an account? <a @click="toggleShowingSignIn">Sign in</a></p>

    <form @submit="submitSignUp">
      <label for="name">Name</label>
      <input type="text" id="name" />
      <br />

      <label for="email">Email</label>
      <input type="email" id="email" />
      <br />

      <label for="phone-number">Phone Number</label>
      <input type="text" id="phone-number" />
      <br />

      <label for="password">Password</label>
      <input type="password" id="password" />
      <br />

      <button>Sign Up</button>
    </form>
  </div>
</template>

<style scoped>
a {
  color: #4287f5;
  cursor: pointer;
}
</style>
