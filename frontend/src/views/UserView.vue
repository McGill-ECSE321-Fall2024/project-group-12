<script setup>
import { inject, ref } from 'vue'
import SigninView from '@/views/SigninView.vue'
import Order from '@/components/Order.vue'
// load the current user
const { user, signOut, updateUser, token } = inject('auth')
console.log('user view loaded')
// createThemeFromColour('#FF9797')

const updateInfo = async (event) => {
  event.preventDefault()
  console.log('hai')
  const form = event.target
  // load the data from each input
  console.log(form.querySelector('#name'))
  const name = form.querySelector('#name').value
  const email = form.querySelector('#email').value
  const phoneNumber = form.querySelector('#phoneNumber').value

  console.log('hai')
  updateUser(name, email, phoneNumber)
}

async function getOrders() {
  const authResponse = JSON.parse(localStorage.getItem('auth'))
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
  <!-- login page can go here on mobile, on desktop it'll be a popup -->
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
        <div class="password-container">
          <input type="password" value="password" readonly />
          <button type="password" class="edit-button">Edit</button>
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
.user-info label {
  font-size: 0.75rem;
  padding: 0.6rem 0rem 0.5rem 0rem;
}
.user-info input {
  padding: 0rem 1rem 1rem 1rem;
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
</style>
