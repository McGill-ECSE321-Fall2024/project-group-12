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
    <h1 style="font-size: xxx-large;">Sign In</h1>
    <p style="font-size: x-large;">Don't have an account? <a @click="toggleShowingSignIn">Sign up</a></p>

    <form @submit="submitSignIn">
      <div class="form-element">
        <label for="email">Email</label>
        <input type="email" id="email" placeholder="email" />
      </div>
      <div class="form-element">
        <label for="password">Password</label>
        <input type="password" id="password"  placeholder="password"/>
      </div>
      <button>Sign In</button>
    </form>
  </div>
  <div v-else class="signup-container">
    <h1 style="font-size: xxx-large;">Sign Up</h1>
    <p style="font-size: x-large;">Already have an account? <a @click="toggleShowingSignIn">Sign in</a></p>

    <form @submit="submitSignUp">
      <div class="form-element">
        <label for="name">Name</label>
        <input type="text" id="name" placeholder="name"/>
      </div>

      <div class="form-element">
        <label for="email">Email</label>
        <input type="email" id="email" placeholder="email" />
      </div>

      <div class="form-element">
        <label for="phone-number">Phone Number</label>
        <input type="text" id="phone-number" placeholder="phone number" />
      </div>

      <div class="form-element">
        <label for="password">Password</label>
        <input type="password" id="password" placeholder="password" />
      </div>

      <button>Sign Up</button>
    </form>
  </div>
</template>

<style scoped>
a {
  color: #4287f5;
  cursor: pointer;
}
.signin-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
  justify-items: center;
}
.signup-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
  justify-items: center;
}
form {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 40%;
  margin-top: 20px;
}
.form-element {
  margin-bottom: 20px;
  width: 100%;
  display: flex;
  flex-direction: column;
  font-size: x-large;
}
input {
  height: 48px;
  border-radius: 8px;
  padding-left: 10px;
}
button {
  width: 30%;
  height: 48px;
  border: none;
  border-radius: 24px;
  padding-left: 10px;
  background-color: green;
  font-size: large;
  color: white;
}
@media screen and (max-width: 600px) {
  .signin-container {
    width: 100%;
  }
  .signup-container {
    width: 100%;
  }
  form {
    width: 100%;
  }
  button {
    width: 50%;
  }
}
</style>
