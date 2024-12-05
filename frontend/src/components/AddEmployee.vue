<script setup>
import { ref } from 'vue'

const emit = defineEmits(['addEmployee', 'closeForm'])

const employee = ref({
  name: '',
  email: '',
  phoneNumber: '',
  password: '',
  active: true,
})

const handleSubmit = () => {
  emit('addEmployee', { ...employee.value })
  emit('closeForm')
}

const handleCancel = () => {
  emit('closeForm')
}
</script>

<template>
  <div class="overlay">
    <div class="form-container">
      <h2>Add New Employee</h2>
      <form @submit.prevent="handleSubmit">
        <label for="name">Name</label>
        <input id="name" v-model="employee.name" type="text" required />

        <label for="email">Email</label>
        <input id="email" v-model="employee.email" type="email" required />

        <label for="phone">Phone Number</label>
        <input id="phone" v-model="employee.phoneNumber" type="text" required />

        <label for="password">Password</label>
        <input id="password" v-model="employee.password" type="password" required />

        <div class="button-group">
          <button type="submit" class="submit-button">Submit</button>
          <button type="button" class="cancel-button" @click="handleCancel">Cancel</button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.form-container {
  background: grey;
  padding: 20px;
  border-radius: 8px;
  width: 400px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

h2 {
  margin-bottom: 20px;
  text-align: center;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input {
  width: 100%;
  padding: 8px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.button-group {
  display: flex;
  justify-content: space-between;
}

.submit-button {
  background-color: #4caf50;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
}

.cancel-button {
  background-color: #f44336;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  opacity: 0.9;
}
</style>
