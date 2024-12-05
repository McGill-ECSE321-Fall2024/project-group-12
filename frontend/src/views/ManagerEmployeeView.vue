<!--
 Manager Employee page
 @author Carmin Vidé
-->
<script setup>
import { ref } from 'vue'
import { inject } from 'vue'
import listItem from '@/components/EmployeeListItem.vue'
import AddEmployeeForm from '@/components/AddEmployee.vue'

const { createThemeFromColour } = inject('theme')
// change to a red theme to match the holiday effect
createThemeFromColour('#FF9797')

//
const { token } = inject('auth')
// load all games from the db
const showAddForm = ref(false)

const employees = ref([])
const response = await fetch('http://localhost:8080/employees', {
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
employees.value = await response.json()

const fetchEmployees = async () => {
  const response = await fetch('http://localhost:8080/employees', {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${token.value}`,
    },
  })

  if (response.ok) {
    employees.value = await response.json()
  } else {
    console.error('Failed to fetch employees')
  }
}

// Fetch Employees when the component is mounted
await fetchEmployees()

// Function to handle the delete action
const handleEmployeeDelete = async () => {

  // Re-fetch the Employee list after deletion
  location.reload()
}


// Function to add a new employee
const handleAddEmployee = async (newEmployee) => {
  const response = await fetch('http://localhost:8080/employees', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token.value}`,
    },
    body: JSON.stringify(newEmployee),
  })
  if (response.ok) {
    console.log('Employee added successfully')
    await fetchEmployees() // Refresh the list
  } else {
    console.error('Failed to add employee')
  }
}

// Example employee details for simplicity
const newEmployee = ref({
  name: '',
  email: '',
  phoneNumber: '',
  password: '',
  active: true,
})
//
</script>

<template>
  <main>
    <div v-if="employees.filter(employee => employee.active).length > 0">
      <h1 class="title left-aligned">Active Employees</h1>
      <button @click="showAddForm = true" class="add-button">Add Employee</button>
      <AddEmployeeForm
      v-if="showAddForm"
      @addEmployee="handleAddEmployee"
      @closeForm="showAddForm = false"
      />
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

      <!-- only show active employees here -->
      <div>
        <listItem
          v-for="employee in employees.filter(employee => employee.active)"
          :key="employee.id"
          :id="employee.id"
          :name="employee.name"
          :email="employee.email"
          :phoneNumber="employee.phoneNumber"
          :active="employee.active"
          @deleteEmployee="handleEmployeeDelete"
        />
      </div>
    </div>

    <div v-if="employees.filter(employee => !employee.active).length > 0">
      <h1 class="title left-aligned">Inactive Employees</h1>

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

      <!-- only show active employees here -->
      <div>
        <listItem
          v-for="employee in employees.filter(employee => !employee.active)"
          :key="employee.id"
          :id="employee.id"
          :name="employee.name"
          :email="employee.email"
          :phoneNumber="employee.phoneNumber"
          deleteEmployee="handleEmployeeDelete"
        />
      </div>
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
