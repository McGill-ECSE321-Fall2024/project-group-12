<!--
 Manager Customer page
 @author Carmin Vidé
-->
<script setup>
import { ref } from 'vue'
import { inject } from 'vue'
import listItem from '@/components/CustomerListItem.vue'
const { createThemeFromColour } = inject('theme')
// change to a red theme to match the holiday effect
createThemeFromColour('#FF9797')

//
const { user, token } = inject('auth')
// load all games from the db
const customers = ref([])
const response = await fetch('http://localhost:8080/customers', {
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
customers.value = await response.json()

const fetchCustomers = async () => {
  const response = await fetch('http://localhost:8080/customers', {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${token.value}`,
    },
  })

  if (response.ok) {
    customers.value = await response.json()
  } else {
    console.error('Failed to fetch customers')
  }
}

// Fetch customers when the component is mounted
await fetchCustomers()

// Function to handle the delete action
const handleCustomerDelete = async () => {
  // Re-fetch the customer list after deletion
  await fetchCustomers()
}
//

const customers_ex = ref([
  { id: '89023794', name: 'Cunégonde Theodraalia', email: 'laplusbellecune@hotmail.dk' },
  { id: '12345678', name: 'John Doe', email: 'john.doe@example.com' },
  { id: '87654321', name: 'Jane Smith', email: 'jane.smith@example.com' },
  { id: '13579246', name: 'Antoine Dupont', email: 'antoine.dupont@example.fr' },
  { id: '24681357', name: 'María García', email: 'maria.garcia@example.es' },
  { id: '87390', name: 'María García', email: 'maria.garcia@example.es' },
  { id: '24681357', name: 'María García', email: 'maria.garcia@example.es' },
  { id: '24681357', name: 'María García', email: 'maria.garcia@example.es' },
])
</script>

<template>
  <main>
    <div>
      <h1 class="title left-aligned">Customers</h1>
    </div>
    <div class="topbar">
      <div class="rectangle parent">
        <h2 class="children id">ID</h2>
        <h2 class="children name">Name</h2>
        <h2 class="children email">Email</h2>
        <h2 class="children phone">Phone Number</h2>
        <h2 class="children action">Actions</h2>
      </div>
      <hr class="line_top" width="100%" size="3" />
    </div>

    <div>
      <listItem
        v-for="customer in customers"
        :key="customer.id"
        :id="customer.id"
        :name="customer.name"
        :email="customer.email"
        :phoneNumber="customer.phoneNumber"
        @deleteCustomer="handleCustomerDelete"
      />
    </div>
  </main>
</template>

<style scoped>
h1 {
  color: #ffffff;
  text-align: center;
  font-size: 36px;
}

.left-aligned {
  text-align: left;
}
.title {
  display: flex;
  flex-direction: column;
  margin-left: 10px;
}
.icon {
  width: 48px;
  height: 48px;
}

.rectangle {
  width: 100%;
  height: 55px;
  border-radius: 3px;
  background: Gray;
}

.line_top {
  position: relative;

  margin-bottom: 0px;
}

.children {
  margin: 10px;
}

.id {
  width: 12%;
  flex-shrink: 0;
  text-align: left;
}

.name {
  width: 25%;
  flex-shrink: 0;
  text-align: left;
}
.email {
  width: 30%;
  flex-shrink: 0;
  text-align: left;
}
.phone {
  width: 18.5%;
  flex-shrink: 0;
  text-align: left;
}
.action {
  width: 13%;
  flex-shrink: 0;
  text-align: left;
}

.parent {
  position: relative;
  display: flex;
  flex-direction: row;
  /*justify-content: space-between;*/
  align-items: center;
  padding: 20px;
}

.topbar {
  display: flex;
  flex-direction: column;
  margin-top: 20px;
}
</style>
