<!--
 Manager Account page
 @author Carmin Vidé
-->
<script setup>
import { inject, ref } from 'vue'
import SigninView from '@/views/SigninView.vue'
// load the current user
const { user, signOut, token, userType } = inject('auth')
const showPasswordPopup = ref(false)

// if the current user is an employee, redirect to their specific page
if (userType.value == 'EMPLOYEE') {
  console.log('redirecting to employee page...')
  location.href = `http://localhost:5173/manager/employee/${user.value.id}`
}

const updateUser = async (name, email, phoneNumber, password) => {
  const resp = await fetch(`http://localhost:8080/manager`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token.value}`,
      Accept: 'application/json',
    },
    body: JSON.stringify({
      name,
      email,
      phoneNumber,
      password,
    }),
  })

  if (!resp.ok) {
    throw new Error(`HTTP error! status: ${resp.status}`)
  }
  // read the response
  const data = await resp.json()
  const id = data.id
  // store the data
  localStorage.setItem(
    'auth',
    JSON.stringify({
      token: token.value,
      id,
      userType: 'MANAGER',
    }),
  )
  alert('updated')
}
console.log('manager view loaded')

const updateInfo = async (event) => {
  event.preventDefault()
  console.log('updating info')
  const form = event.target
  const name = form.querySelector('#name').value
  const email = form.querySelector('#email').value
  const phoneNumber = form.querySelector('#phoneNumber').value
  const password = form.querySelector('#password').value
  updateUser(name, email, phoneNumber, password)
}

const togglePasswordPopup = () => {
  showPasswordPopup.value = !showPasswordPopup.value
}

async function updatePassword(event) {
  event.preventDefault()
  console.log('updating password')
  const email = user.value.email
  const form = event.target
  const oldPassword = form.querySelector('#oldPassword').value
  const newPassword = form.querySelector('#newPassword').value
  const authResponse = JSON.parse(localStorage.getItem('auth'))
  const { id } = authResponse
  const resp = await fetch(`http://localhost:8080/customers/auth/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token.value}`,
      Accept: 'application/json',
    },
    body: JSON.stringify({
      oldPassword: oldPassword,
      newPassword: newPassword,
      email: email,
    }),
  })

  const data = await resp.json()
  if (data.errors) {
    alert(data.errors)
  } else {
    alert('Password successfully changed!')
  }

  togglePasswordPopup()
}
</script>

<template>
  <!-- IF current user is null -->
  <SigninView v-if="user == null" />
  <!-- otherwise, the normal page can be shown -->
  <div v-else class="user">
    <h2 class="title">Profile</h2>

    <div class="grid-container">
      <form class="user-info" @submit.prevent="updateInfo">
        <label for="name">Name</label>
        <input
          type="name"
          id="name"
          :value="user.name"
          @input="(event) => (user.name = event.target.value)"
        />
        <label for="email">Email</label>
        <input
          type="email"
          id="email"
          :value="user.email"
          @input="(event) => (user.email = event.target.value)"
        />
        <label for="password">Password</label>
        <input type="password" placeholder="Enter current password or new password" id="password" required />
        <label for="phoneNumber">Phone Number</label>
        <input
          type="phoneNumber"
          id="phoneNumber"
          :value="user.phoneNumber"
          @input="(event) => (user.phoneNumber = event.target.value)"
        />
        <button class="update-button">Update</button>
        <button class="signout-button" @click="signOut">Sign out</button>
      </form>

      <div v-if="showPasswordPopup" class="popup">
        <div class="popup-content">
          <h2>Change Password</h2>
          <form @submit.prevent="updatePassword">
            <label>Current Password</label>
            <input type="password" id="oldPassword" required />
            <label>New Password</label>
            <input type="password" id="newPassword" required />
            <div class="popup-buttons">
              <button type="submit">Save</button>
              <button type="button" @click="togglePasswordPopup">Cancel</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.title {
  display: inline;
  font-size: 3rem;
}

.grid-container {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-template-rows: repeat(2, 1fr);
  grid-column-gap: 2rem;
  grid-row-gap: 2rem;
}

* {
  color: white;
}
.user {
  padding: 3%;
}
.user-info {
  display: inline-flex;
  flex-direction: column;
  grid-area: 1 / 1 / 3 / 2;
}
label {
  font-size: 0.75rem;
  padding: 0.6rem 0rem 0.5rem 0rem;
}
input {
  padding: 0.3rem 1rem 0.3rem 1rem;
  background-color: inherit;
  color: inherit;
  font-size: 1.5rem;
  border: none;
  border-bottom: 1px solid grey;
}
.user-info input:focus {
  border-bottom: 1px solid #a23e48;
  outline: none;
}
.password-container {
  display: flex;
  justify-content: space-between;
}
.password-container > input {
  width: 90%;
}
.password-container > button {
  width: 10%;
  border: 0;
}
button {
  background: inherit;
  color: #ffffff;
  border: none;
  border-radius: 100px;
  padding: 0.75rem;
  margin: 1px 0px;
}
.signout-button {
  background: linear-gradient(90deg, rgb(162, 62, 72, 1) 30%, rgba(65, 93, 67, 1) 70%);
}
.update-button {
  background: linear-gradient(90deg, rgba(65, 93, 67, 1) 30%, rgba(162, 62, 72, 1) 70%);
  margin-top: 1rem;
}
.edit-button {
  font-size: 1rem;
  border-bottom: 1px solid white;
  border-radius: 0px;
  padding: 0;
}
.shipping-address {
  grid-area: 1 / 2 / 2 / 3;
}
.payment-method {
  grid-area: 2 / 2 / 3 / 3;
}
.card {
  background: linear-gradient(90deg, rgba(65, 93, 67, 1) 30%, rgba(162, 62, 72, 1) 70%);
  background: #111111;
  border-radius: 40px;
  padding: 2rem;
  display: flex;
  flex-direction: column;
}
.card > div {
  display: flex;
  justify-content: space-between;
}

.popup {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}
.popup-content {
  background: rgba(65, 93, 67, 1);
  padding: 20px;
  border-radius: 8px;
  min-width: 300px;
}
.popup button {
  background: rgba(162, 62, 72, 1);
  width: 100%;
}

.popup form {
  display: flex;
  flex-direction: column;
}
.popup-buttons {
  margin-top: 1rem;
}
.spacer {
  margin: 3rem;
}

@media only screen and (max-width: 600px) {
  .grid-container {
    grid-template-columns: 1fr;
    grid-template-rows: repeat(3, 1fr);
  }
  .user-info {
    display: inline-flex;
    flex-direction: column;
    grid-area: 1 / 1 / 2 / 2;
  }
  .shipping-address {
    grid-area: 2 / 1 / 3 / 2;
  }
  .payment-method {
    grid-area: 3 / 1 / 4 / 2;
  }
}
</style>
