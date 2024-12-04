<!--
 Cart page
 @author Amy Ding
-->
<script setup>
import { inject, ref } from 'vue'
import SigninView from '@/views/SigninView.vue'
import Order from '@/components/Order.vue'
import BackgroundGradient from '@/components/BackgroundGradient.vue'
// load the current user
const { user, signOut, updateUser, token } = inject('auth')
const { createThemeFromColour } = inject('theme')
const showPasswordPopup = ref(false)
console.log('user view loaded')

// set a green theme for a nice holiday design
createThemeFromColour('#415d43')

const updateInfo = async (event) => {
  event.preventDefault()
  const form = event.target
  const name = form.querySelector('#name').value
  const email = form.querySelector('#email').value
  const phoneNumber = form.querySelector('#phoneNumber').value
  updateUser(name, email, phoneNumber)
}
const togglePasswordPopup = () => {
  if (showPasswordPopup.value) {
    oldPassword.value = ''
    newPassword.value = ''
  }
  showPasswordPopup.value = !showPasswordPopup.value

}
async function updatePassword(event) {
  event.preventDefault()
  const email = user.value.email
  console.log(email)
  const form = event.target
  const oldPassword = form.querySelector('#oldPassword').value
  const newPassword = form.querySelector('#newPassword').value
  console.log('Updating password:', {
    old: oldPassword,
    new: newPassword
  })
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
      email: email
    }),
  })

  const data = await resp.json()
  if (data.errors) {
    alert(data.errors);
  } else {
    alert("Password successfully changed!")
  }

  togglePasswordPopup()
}
async function getOrders() {
  const authResponse = JSON.parse(localStorage.getItem('auth'))
  // check whether auth response exists
  if (!authResponse) return []
  const { token, id, userType } = authResponse
  console.log(authResponse.id)
  const resp = await fetch(`http://localhost:8080/orders/customer/${id}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
      Accept: 'application/json',
    },
  })
  const data = await resp.json()
  return data
}
const orders = ref(null)
orders.value = await getOrders()
orders.value = [
  {
    id: 1,
    games: [
      {
        id: 101,
        name: 'Animal Crossing',
        console: 'Nintendo Switch',
        year: 2014,
        price: 1212.22,
      },
      {
        id: 102,
        name: 'Minecraft',
        console: 'PC',
        year: 1980,
        price: 999.99,
      },
      {
        id: 103,
        name: 'League of Legends',
        console: 'XBox',
        year: 1823,
        price: 1,
      },
    ],
  },
]
</script>

<template>
  <!-- IF current user is null -->
  <SigninView v-if="user == null" />
  <!-- otherwise, the normal page can be shown -->
  <div v-else class="user">
    <BackgroundGradient colour="65, 93, 67" />

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
        <div class="password-container">
          <input type="password" value="password" readonly />
          <button type="password" class="edit-button" @click="togglePasswordPopup">Edit</button>
        </div>
        <label for="phoneNumber">Phone Number</label>
        <input
          type="phoneNumber"
          id="phoneNumber"
          :value="user.phoneNumber"
          @input="(event) => (user.phoneNumber = event.target.value)"
        />
        <button class="update-button">Update</button>
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

      <div class="shipping-address card">
        <div>
          <h2>Shipping Address</h2>
          <button v-if="user.address == null" class="edit-button" @click="editAddress">Add</button>
          <button v-else class="edit-button">Edit</button>
        </div>
        <h3 v-if="user.address == null">No shipping address associated with this account</h3>
        <input v-else type="text" id="address" />
      </div>

      <div class="payment-info card">
        <div>
          <h2>Payment Method</h2>
          <button v-if="user.payment == null" class="edit-button" @click="getOrders">Add</button>
          <button v-else class="edit-button">Edit</button>
        </div>
        <h3 v-if="user.paymentinfo == null">No payment information associated with this account</h3>
        <input v-else type="text" id="payment" />
      </div>
    </div>
    <button @click="signOut">Sign out</button>
    <section>
      <h2 class="title">Orders</h2>
      <div>
        <Order v-for="order in orders" :key="order.id" :order="order" />
      </div>
    </section>
  </div>
</template>

<style scoped>
.title {
  display: inline;
  font-size: 2.25rem;
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
  border-bottom: 1px solid var(--theme-primary);
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

.popup form{
  display: flex;
  flex-direction: column;
}
.popup-buttons {
  margin-top: 1rem;
}
</style>
