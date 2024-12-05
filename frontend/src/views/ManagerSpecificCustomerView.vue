<!--
 Specific customer view for managers
 @author Carmin Vidé
-->
<script setup>
import { inject, ref } from 'vue'
import SigninView from '@/views/SigninView.vue'
import OrderCard from '@/components/OrderCard.vue'

import { useRoute } from 'vue-router'

const route = useRoute()
const customerId = route.params.id

// load the current customer
const { token } = inject('auth')

const customer = ref('')
const response = await fetch(`http://localhost:8080/customers/${customerId}`, {
  method: 'GET',
  headers: {
    Authorization: `Bearer ${token.value}`,
  },
})
if (response.ok) {
  console.log('Request successful')
} else {
  console.log('Request failed')
}
customer.value = await response.json()

const updateUser = async (name, email, phoneNumber, address) => {
  const resp = await fetch(`http://localhost:8080/customers/${customerId}`, {
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
      address,
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
      userType: 'CUSTOMER',
    }),
  )
}
console.log('customer view loaded')

const showAddressPopup = ref(false)
const addressFields = ref({
  address: '',
  apt: '',
  city: '',
  province: '',
  country: '',
  postal: '',
})
console.log('customer view loaded')

const updateInfo = async (event) => {
  event.preventDefault()
  console.log('updating info')
  const form = event.target
  const name = form.querySelector('#name').value
  const email = form.querySelector('#email').value
  const phoneNumber = form.querySelector('#phoneNumber').value
  const address = customer.value.address
  updateUser(name, email, phoneNumber, address)
}

const toggleAddressPopup = () => {
  if (!showAddressPopup.value) {
    populateAddressFields()
  }
  showAddressPopup.value = !showAddressPopup.value
}

async function updateAddress(event) {
  event.preventDefault()
  console.log('updating address')
  const form = event.target
  const addressComponents = [
    form.querySelector('#address').value,
    form.querySelector('#apt').value,
    form.querySelector('#city').value,
    form.querySelector('#province').value,
    form.querySelector('#country').value,
    form.querySelector('#postal').value,
  ]
  const addressLine = addressComponents.join('\\n')
  toggleAddressPopup()
  const email = customer.value.email
  const name = customer.value.name
  const phoneNumber = customer.value.phoneNumber
  updateUser(name, email, phoneNumber, addressLine)
  toggleAddressPopup
  location.reload()
}
const formatAddress = (address) => {
  return address.replace(/\\n\\n/g, '\n').replace(/\\n/g, '\n')
}
const populateAddressFields = () => {
  if (customer.value?.address) {
    const parts = customer.value.address.split('\\n')
    addressFields.value = {
      address: parts[0] || '',
      apt: parts[1] || '',
      city: parts[2] || '',
      province: parts[3] || '',
      country: parts[4] || '',
      postal: parts[5] || '',
    }
  }
}
async function getOrders() {
  const authResponse = JSON.parse(localStorage.getItem('auth'))
  // check whether auth response exists
  if (!authResponse) return []
  const { token, id, customerType } = authResponse
  console.log(authResponse.id)
  const resp = await fetch(`http://localhost:8080/orders/customer/${customerId}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
      Accept: 'application/json',
    },
  })
  const data = await resp.json()
  console.log('THIS IS THE ORDER')
  console.log(data)
  return data
}
const orders = ref(null)
orders.value = await getOrders()
console.log('teehee')
console.log(orders.value[orders.value.length - 1])
console.log('haiiiiiii')
console.log(orders.value)
</script>

<template>
  <!-- IF current customer is null -->
  <SigninView v-if="customer == null" />
  <!-- otherwise, the normal page can be shown -->
  <div v-else class="customer">
    <h2 class="title">Profile</h2>

    <div class="grid-container">
      <form class="customer-info" @submit.prevent="updateInfo">
        <label for="name">Name</label>
        <input
          type="name"
          id="name"
          :value="customer.name"
          @input="(event) => (customer.name = event.target.value)"
        />
        <label for="email">Email</label>
        <input
          type="email"
          id="email"
          :value="customer.email"
          @input="(event) => (customer.email = event.target.value)"
        />

        <label for="phoneNumber">Phone Number</label>
        <input
          type="phoneNumber"
          id="phoneNumber"
          :value="customer.phoneNumber"
          @input="(event) => (customer.phoneNumber = event.target.value)"
        />
        <button class="update-button">Update</button>
      </form>
    </div>

    <div class="shipping-address card">
      <div>
        <h2>Shipping Address</h2>
        <button v-if="customer.address == null" class="edit-button" @click="toggleAddressPopup">
          Add
        </button>
        <button v-else class="edit-button" @click="toggleAddressPopup">Edit</button>
      </div>
      <h3 v-if="customer.address == null">No shipping address associated with this account</h3>
      <span v-else style="white-space: pre"> {{ formatAddress(customer.address) }}</span>
    </div>

    <div v-if="showAddressPopup" class="popup">
      <div class="popup-content">
        <h2>Change Password</h2>
        <form @submit.prevent="updateAddress">
          <label>Address</label>
          <input
            type="text"
            id="address"
            :value="addressFields.address"
            @input="(event) => (addressFields.address = event.target.value)"
            required
          />
          <label>Apt/Building/Other</label>
          <input
            type="text"
            id="apt"
            :value="addressFields.apt"
            @input="(event) => (addressFields.apt = event.target.value)"
          />
          <label>City</label>
          <input
            type="text"
            id="city"
            :value="addressFields.city"
            @input="(event) => (addressFields.city = event.target.value)"
            required
          />
          <label>Province/State/Territory</label>
          <input
            type="text"
            id="province"
            :value="addressFields.province"
            @input="(event) => (addressFields.province = event.target.value)"
            required
          />
          <label>Country</label>
          <input
            type="text"
            id="country"
            :value="addressFields.country"
            @input="(event) => (addressFields.country = event.target.value)"
            required
          />
          <label>Postal Code</label>
          <input
            type="text"
            id="postal"
            :value="addressFields.postal"
            @input="(event) => (addressFields.postal = event.target.value)"
            required
          />
          <div class="popup-buttons">
            <button type="submit">Save</button>
            <button type="button" @click="toggleAddressPopup">Cancel</button>
          </div>
        </form>
      </div>
    </div>
  </div>
  <div class="spacer"></div>
  <section>
    <h2 class="title">Orders</h2>
    <div class="orders-container">
      <OrderCard v-for="order in orders" :order="order" />
      <!-- <OrderCard/> -->
    </div>
  </section>
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
.customer {
  padding: 3%;
}
.customer-info {
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
.customer-info input:focus {
  border-bottom: 1px solid #a23e48;
  outline: none;
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
  .customer-info {
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
