<!--
Specific employee view for managers
@author Carmin Vidé
-->
<script setup>
import { inject, ref } from 'vue'
import SigninView from '@/views/SigninView.vue'

import { useRoute } from 'vue-router'

const route = useRoute()
const employeeId = route.params.id

// load the current employee
const { token } = inject('auth')

const employee = ref('')
const response = await fetch(`http://localhost:8080/employees/${employeeId}`, {
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
employee.value = await response.json()

const updateUser = async (name, email, phoneNumber, password) => {
  const resp = await fetch(`http://localhost:8080/employees/${employeeId}`, {
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
      password
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
      userType: 'EMPLOYEE',
    }),
  )
  alert("updated")
}
console.log('employee view loaded')

const updateInfo = async (event) => {
  event.preventDefault()
  console.log('updating info')
  const form = event.target
  const name = form.querySelector('#name').value
  const email = form.querySelector('#email').value
  const phoneNumber = form.querySelector('#phoneNumber').value
  const address = employee.value.address
  updateUser(name, email, phoneNumber, address)
}
</script>

<template>
  <!-- IF current employee is null -->
  <SigninView v-if="employee == null" />
  <!-- otherwise, the normal page can be shown -->
  <div v-else class="employee">
    <h2 class="title">Profile</h2>

    <div class="grid-container">
      <form class="employee-info" @submit.prevent="updateInfo">
        <label for="name">Name</label>
        <input
          type="name"
          id="name"
          :value="employee.name"
          @input="(event) => (employee.name = event.target.value)"
        />
        <label for="email">Email</label>
        <input
          type="email"
          id="email"
          :value="employee.email"
          @input="(event) => (employee.email = event.target.value)"
        />

        <label for="phoneNumber">Phone Number</label>
        <input
          type="phoneNumber"
          id="phoneNumber"
          :value="employee.phoneNumber"
          @input="(event) => (employee.phoneNumber = event.target.value)"
        />
        <button class="update-button">Update</button>
      </form>
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
.employee {
  padding: 3%;
}
.employee-info {
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
.employee-info input:focus {
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
  .employee-info {
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
