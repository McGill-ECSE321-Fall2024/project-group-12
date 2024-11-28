<!-- displays different pages depending on the current auth state -->
<script setup>
import { inject } from 'vue'

const { userType } = inject('auth')
</script>

<template>
  <div v-if="userType === 'MANAGER'">
    <slot name="manager"></slot>
  </div>
  <div v-else-if="userType === 'EMPLOYEE'">
    <slot name="employee"></slot>
  </div>
  <div v-else>
    <slot name="customer"></slot>
  </div>
</template>
